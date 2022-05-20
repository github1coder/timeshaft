package com.timeshaft.after_end.service.timeshaftOp;


import com.timeshaft.after_end.annotation.PermissionAnnotation;
import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@PropertySource("classpath:content.properties")
public class TimeShaftOp {
    @Autowired
    private TimeshaftService timeshaftService;
    @Autowired
    private TagService tagService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private FriendsService friendsService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupMessageService groupMessageService;
    @Autowired
    private PersonalMessageService personalMessageService;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Value("${meeting.on}")
    private String OnMeeting;
    @Value("${meeting.off}")
    private String OffMeeting;

    @Value("${type.friendType}")
    private String friendType;
    @Value("${type.groupType}")
    private String groupType;


    //    @PermissionAnnotation(level=12)
    public Integer beginTimeShaftSingle(String title, String conclude, Integer user_id, Integer group_id, String type, ArrayList<String> tags) throws Exception {
        Timeshaft timeshaft = new Timeshaft(group_id, user_id, title, new Date(), null, conclude, type,
                0, -1, -1, randomKey());
        timeshaft = timeshaftService.insert(timeshaft);
        checkGroupState(group_id, type, OnMeeting);
        changeGroupState(group_id, type, OnMeeting);
        for (String tag : tags) {
            Tag newTag = new Tag(timeshaft.getId(), tag);
            tagService.insert(newTag);
        }
        return timeshaft.getId();
    }

    //    @PermissionAnnotation(level=3)
    public List<Map<String, Object>> getTimeshafts(Integer group_id, String type, Integer user_id) {
        Timeshaft timeshaftTemp = new Timeshaft(group_id, null, null, null, null, null, type,
                null, null, null, randomKey());
        List<Timeshaft> timeshafts = timeshaftService.queryAll(timeshaftTemp);
        List<Map<String, Object>> timeshaftsRes = new ArrayList<>();
        for (Timeshaft timeshaft : timeshafts) {
            if (timeshaft.getEndTime() != null) {
                Map<String, Object> timeshaftRes = new HashMap<>();
                User user = userService.queryById(timeshaft.getCreatorId());
                timeshaftRes.put("title", timeshaft.getName());
                timeshaftRes.put("img", user.getPhoto());
                timeshaftRes.put("begin_date", timeshaft.getBeginTime());
                timeshaftRes.put("end_date", timeshaft.getBeginTime());
                timeshaftRes.put("conclude", timeshaft.getConclude());
                timeshaftRes.put("host", user.getUsername());

                List<Tag> tags = tagService.queryAll(new Tag(timeshaft.getId(), null));
                List<String> tagsRes = new ArrayList<>();
                for (Tag tag : tags) {
                    tagsRes.add(tag.getName());
                }
                timeshaftRes.put("tags", tagsRes);
                timeshaftsRes.add(timeshaftRes);
            }
        }
        return timeshaftsRes;
    }

    @PermissionAnnotation(level = 38)
    public void endTimeShaft(Integer group_id, String type, Integer user_id) throws Exception {
        List<Timeshaft> timeshafts = timeshaftService.queryAll(new Timeshaft(group_id, null, null, null, null, null, type,
                null, null, null, randomKey()));
        for (Timeshaft timeshaft : timeshafts) {
            Date end_time = new Date();
            timeshaft.setEndTime(end_time);
            if (groupType.equals(type)) {
                List<GroupMessage> groupMessages = groupMessageService.queryTimeshaft(timeshaft);
                if (!groupMessages.isEmpty()) {
                    timeshaft.setStartMsgId(groupMessages.get(0).getId());
                    timeshaft.setEndMsgId(groupMessages.get(groupMessages.size() - 1).getId());
                }
            } else if (friendType.equals(type)) {
                List<PersonalMessage> personalMessages = personalMessageService.queryTimeshaft(timeshaft);
                if (!personalMessages.isEmpty()) {
                    timeshaft.setStartMsgId(personalMessages.get(0).getId());
                    timeshaft.setEndMsgId(personalMessages.get(personalMessages.size() - 1).getId());
                }
            } else {
                throw new Exception("type变量错误");
            }
            timeshaftService.update(timeshaft);
        }
        checkGroupState(group_id, type, OffMeeting);
        changeGroupState(group_id, type, OffMeeting);
    }

    private void changeGroupState(Integer group_id, String type, String status) throws Exception {
        if (groupType.equals(type)) {
            Group group = groupService.queryById(group_id);
            group.setStatus(status);
            groupService.update(group);
        } else if (friendType.equals(type)) {
            Friends friend = friendsService.queryById(group_id);
            friend.setStatus(status);
            friendsService.update(friend);
        } else {
            throw new Exception("type变量错误");
        }
    }

    private void checkGroupState(Integer group_id, String type, String status) throws Exception {
        if (groupType.equals(type)) {
            Group group = groupService.queryById(group_id);
            if (group.getStatus().equals(status)) {
                throw new Exception("不要在开会的时候继续开会或没开会就结束开会啦，八嘎！");
            }
        } else if (friendType.equals(type)) {
            Friends friend = friendsService.queryById(group_id);
            if (friend.getStatus().equals(status)) {
                throw new Exception("不要在开会的时候继续开会或没开会就结束开会啦，八嘎！");
            }
        } else {
            throw new Exception("type变量错误");
        }
    }

    private Object getTimeShaftMessage(Timeshaft timeshaft) {
        if (groupType.equals(timeshaft.getType())) {
            return groupMessageService.queryTimeshaft(timeshaft);
        } else if (friendType.equals(timeshaft.getType())) {
            return personalMessageService.queryTimeshaft(timeshaft);
        } else {
            return null;
        }
    }

    public List<Map<String, Object>> getTimeShaftData(String start, String end) throws ParseException {
        ArrayList<Map<String, Object>> res = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start_time = sdf.parse(start);
        Date end_time = sdf.parse(end);
        List<Timeshaft> timeshafts = timeshaftService.queryTimeshaftByTime(start_time, end_time);
        for (Timeshaft timeshaft : timeshafts) {
            Map<String, Object> out = new HashMap<>();
            out.put("name", timeshaft.getName());
            if (timeshaft.getBeginTime().compareTo(start_time) < 0) {
                out.put("start", start_time);
            } else {
                out.put("start", timeshaft.getBeginTime());
            }
            if (timeshaft.getEndTime().compareTo(end_time) > 0) {
                out.put("end", end_time);
            } else {
                out.put("end", timeshaft.getEndTime());
            }
            out.put("timed", "false");
            res.add(out);
        }
        return res;
    }

    public void sendNotification(String msgType, Integer chatId, Integer user_id, String operation) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("msgType", msgType);
        res.put("chatId", chatId);
        res.put("operation", operation);
        messagingTemplate.convertAndSend("/user/timeshaft/" + user_id, res);
    }

    @PermissionAnnotation(level = 38)
    public void genTimeShaftFromMessages(int group_id, int user_id, String title, ArrayList<String> tags, String conclude, String type, ArrayList<Integer> msgIds) throws Exception {
        int max = 0, min = 99999999;
        Date date1;
        Date date2;
        if (!msgIds.isEmpty()) {
            for (Integer integer : msgIds) {
                if (integer > max) {
                    max = integer;
                }
                if (integer < min) {
                    min = integer;
                }
            }
            if (groupType.equals(type)) {
                if (groupMessageService.queryById(max) == null || groupMessageService.queryById(min) == null) {
                    throw new Exception("传入消息列表错误");
                }
                date1 = groupMessageService.queryById(min).getSendtime();
                date2 = groupMessageService.queryById(max).getSendtime();
            } else if (friendType.equals(type)) {
                if (personalMessageService.queryById(max) == null || personalMessageService.queryById(min) == null) {
                    throw new Exception("传入消息列表错误");
                }
                date1 = personalMessageService.queryById(min).getSendtime();
                date2 = personalMessageService.queryById(max).getSendtime();
            } else {
                throw new Exception("type参数类型错误");
            }

        } else {
            max = -1;
            min = -1;
            date1 = new Date();
            date2 = new Date();
        }
        Timeshaft timeshaft = new Timeshaft(group_id, user_id, title, date1, date2, conclude, type, 0, min, max, randomKey());
        timeshaftService.insert(timeshaft);
        for (String tag : tags) {
            Tag newTag = new Tag(timeshaft.getId(), tag);
            tagService.insert(newTag);
        }
    }

    @PermissionAnnotation(level = 31)
    public Map<String, Object> getSingleTimeshaft(int user_id, int timeshaft_id) {
        Map<String, Object> ans = new HashMap<>();
        Timeshaft timeshaft = timeshaftService.queryById(timeshaft_id);
        ans.put("name", userService.queryById(timeshaft.getCreatorId()).getUsername());
        ans.put("startTime", timeshaft.getBeginTime());
        ans.put("endTime", timeshaft.getEndTime());
        ans.put("title", timeshaft.getName());
        List<Tag> tags = tagService.queryAll(new Tag(timeshaft_id, null));
        ArrayList<String> tagNames = new ArrayList<>();
        for (Tag tag : tags) {
            tagNames.add(tag.getName());
        }
        ans.put("tags", tagNames);
        ans.put("conclude", timeshaft.getConclude());
        ArrayList<Map<String, Object>> msg = new ArrayList<>();
        if (timeshaft.getType().equals(groupType)) {
            ans.put("groupName", groupService.queryById(timeshaft.getGroupId()).getName());
            if (timeshaft.getStartMsgId() != -1) {
                List<GroupMessage> groupMessages = groupMessageService.queryBeginEndMsg(timeshaft.getGroupId(), timeshaft.getStartMsgId(), timeshaft.getEndMsgId());
                for (GroupMessage groupMessage : groupMessages) {
                    HashMap<String, Object> res = new HashMap<>();
                    res.put("msgFromName", userService.queryById(groupMessage.getSenderId()).getUsername());
                    res.put("msgFromAvatar", userService.queryById(groupMessage.getSenderId()).getPhoto());
                    res.put("time", groupMessage.getSendtime());
                    res.put("msg", groupMessage.getMessage());
                    msg.add(res);
                }
            }
        } else {
            ans.put("groupName", userService.queryById(timeshaft.getGroupId()).getUsername());
            if (timeshaft.getStartMsgId() != -1) {
                List<PersonalMessage> personalMessages = personalMessageService.queryBeginEndMsg(timeshaft.getGroupId(), timeshaft.getStartMsgId(), timeshaft.getEndMsgId());
                for (PersonalMessage personalMessage : personalMessages) {
                    HashMap<String, Object> res = new HashMap<>();
                    res.put("msgFromName", userService.queryById(personalMessage.getSenderId()).getUsername());
                    res.put("msgFromAvatar", userService.queryById(personalMessage.getSenderId()).getPhoto());
                    res.put("time", personalMessage.getSendtime());
                    res.put("msg", personalMessage.getMessage());
                    msg.add(res);
                }
            }
        }
        ans.put("message", msg);
        ans.put("id", timeshaft.getId().toString());
        ans.put("key", "#"+timeshaft.getKey()+"-"+timeshaft.getName());
        return ans;
    }

    public int getIdByKey(String key) {
        int timeshaft_id;
        try {
            key = key.substring(1).split("-")[0];
            String name = key.substring(1).split("-")[1];
            List<Timeshaft> timeshafts = timeshaftService.queryAll(new Timeshaft(null, null, name, null,
                    null, null, null, null, null, null, key));
            timeshaft_id = timeshafts.get(0).getId();
        } catch (Exception e) {
            timeshaft_id = -1;
        }
        return timeshaft_id;
    }

    private String randomKey() {
        String strAll = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //定义一个结果
        String result = "";
        //实例化Random对象
        Random random = new Random();
        //使用for循环得到6为字符
        while (true) {
            for (int i = 0; i < 6; i++) {
                //返回一个小于62的int类型的随机数
                int rd = random.nextInt(52);
                //随机从指定的位置开始获取一个字符
                String oneChar = strAll.substring(rd, rd + 1);
                //循环加到6为
                result += oneChar;
            }
            if(timeshaftService.queryAll(new Timeshaft(null, null, null, null,
                    null, null ,null, null, null, null, result)).isEmpty()) {
                break;
            } else {
                result = "";
            }
        }
        return result;
    }
}
