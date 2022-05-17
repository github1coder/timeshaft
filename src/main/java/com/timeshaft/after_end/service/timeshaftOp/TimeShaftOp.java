package com.timeshaft.after_end.service.timeshaftOp;


import com.timeshaft.after_end.annotation.PermissionAnnotation;
import com.timeshaft.after_end.entity.*;
import com.timeshaft.after_end.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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

    @Value("${meeting.on}")
    private String OnMeeting;
    @Value("${meeting.off}")
    private String OffMeeting;

    @Value("${type.friendType}")
    private String friendType;
    @Value("${type.groupType}")
    private String groupType;

//    @PermissionAnnotation(level=2)
    public Integer beginTimeShaftSingle(String title, String conclude, Integer user_id, Integer group_id, String type, ArrayList<String> tags) throws Exception {
        Timeshaft timeshaft = new Timeshaft(group_id, user_id, title, new Date(), null, conclude, type);
        timeshaft = timeshaftService.insert(timeshaft);
        changeGroupState(group_id, type, OnMeeting);
        for (String tag : tags) {
            Tag newtag = new Tag(timeshaft.getId(), tag);
            tagService.insert(newtag);
        }
        return timeshaft.getId();
    }

//    @PermissionAnnotation(level=3)
    public List<Map<String, Object>> getTimeshaft(Integer group_id, String type, Integer user_id) {
        Timeshaft timeshaftTemp = new Timeshaft(group_id, null, null, null, null, null, type);
        List<Timeshaft> timeshafts = timeshaftService.queryAll(timeshaftTemp);
        List<Map<String, Object>> timeshaftsRes = new ArrayList<>();
        for (Timeshaft timeshaft : timeshafts) {
            if (timeshaft.getEndTime()!=null) {
                Map<String, Object> timeshaftRes = new HashMap<>();
                User user = userService.queryById(timeshaft.getCreatorId());
                timeshaftRes.put("title", timeshaft.getName());
                timeshaftRes.put("img", user.getPhoto());
                timeshaftRes.put("begin_date", timeshaft.getBeginTime());
                timeshaftRes.put("last_time", (timeshaft.getEndTime().getTime()-timeshaft.getBeginTime().getTime())/1000/60);
                timeshaftRes.put("conclude", timeshaft.getConclude());
                timeshaftRes.put("host", user.getUsername());

                List<Tag> tags = tagService.queryAll(new Tag(timeshaft.getId(), null));
                List<String> tagsRes = new ArrayList<>();
                for (Tag tag : tags) {
                    tagsRes.add(tag.getName());
                }
                timeshaftRes.put("tags", tagsRes);

                Object messages = getTimeShaftMessage(timeshaft);
                timeshaftRes.put("messages", messages);
                timeshaftsRes.add(timeshaftRes);
            }
        }
        return timeshaftsRes;
    }

//    @PermissionAnnotation(level=2)
    public void endTimeShaft(Integer group_id, String type, Integer user_id) throws Exception {
        List<Timeshaft> timeshafts = timeshaftService.queryAll(new Timeshaft(group_id,null,null,null,null,null,type));
        for (Timeshaft timeshaft : timeshafts) {
            timeshaft.setEndTime(new Date());
            timeshaftService.update(timeshaft);
        }
        changeGroupState(group_id, type, OffMeeting);
    }

    private void changeGroupState(Integer group_id, String type, String status) throws Exception {
        if (groupType.equals(type)) {
            Group group = groupService.queryById(group_id);
            group.setStatus(status);
            groupService.update(group);
        }
        else if (friendType.equals(type)) {
            Friends friend = friendsService.queryById(group_id);
            friend.setStatus(status);
            friendsService.update(friend);
        }
        else {
            throw new Exception("type变量错误");
        }
    }

    private Object getTimeShaftMessage(Timeshaft timeshaft) {
        if (groupType.equals(timeshaft.getType())) {
            return groupMessageService.queryTimeshaft(timeshaft);
        }
        else if (friendType.equals(timeshaft.getType())) {
            return personalMessageService.queryTimeshaft(timeshaft);
        }
        else {
            return null;
        }
    }

    public List<Map<String, String>> getTimeShaftData(String start, String end) throws ParseException {
        ArrayList<Map<String, String>> res = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start_time = sdf.parse(start);
        Date end_time = sdf.parse(end);
        List<Timeshaft> timeshafts = timeshaftService.queryTimeshaftByTime(start_time, end_time);
        for(Timeshaft timeshaft : timeshafts) {
            Map<String, String> out = new HashMap<>();
            out.put("name", timeshaft.getName());
            if(timeshaft.getBeginTime().compareTo(start_time) < 0) {
                out.put("start", start_time.toString());
            } else {
                out.put("start", timeshaft.getBeginTime().toString());
            }
            if(timeshaft.getEndTime().compareTo(end_time) > 0) {
                out.put("end", end_time.toString());
            } else {
                out.put("end", timeshaft.getEndTime().toString());
            }
            out.put("timed", "false");
            res.add(out);
        }
        return res;
    }
}
