package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.timeshaftOp.TimeShaftOp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@Controller
@RestController //等价于@Controller+@ResponseBody的结合，里面的方法都以json格式输出
@RequestMapping("/timeshaft")  //映射URL
@CrossOrigin
@Slf4j
public class TimeShaftController {
    @Autowired
    private TimeShaftOp timeShaftOp;

    @Value("${meeting.on}")
    private String onMeeting;
    @Value("${meeting.off}")
    private String offMeeting;

    @RequestMapping("/beginTimeShaftSingle")
    public ResponseService beginTimeShaftSingle(@RequestBody Map<String, Object> requestMap) throws Exception {
        log.info("创建时间轴开始");
        String title = (String) requestMap.get("title");
        String conclude = (String) requestMap.get("conclude");
        Integer creator_id = (Integer) requestMap.get("creator_id");
        Integer group_id = (Integer) requestMap.get("group_id");
        String type = (String) requestMap.get("type");
        ArrayList<String> tags = (ArrayList<String>) requestMap.get("tags");
        Integer timeshaft_id = timeShaftOp.beginTimeShaftSingle(title, conclude, creator_id, group_id, type, tags);
        log.info("创建时间轴成功");
        Map<String, Integer> res = new HashMap<>();
        res.put("timeshaft_id", timeshaft_id);
        return new ResponseService(res);
    }

    @RequestMapping("/getTimeshaft")
    public ResponseService getTimeshafts(@RequestBody Map<String, Object> requestMap, @RequestHeader("user_id") Integer user_id) throws Exception {
        log.info("获取时间轴开始");
        Integer group_id = (Integer) requestMap.get("group_id");
        String type = (String) requestMap.get("type");
        List<Map<String, Object>> timeshaftsRes = timeShaftOp.getTimeshafts(group_id, type, user_id);
        log.info("获取时间轴成功");
        Map<String, Object> res = new HashMap<>();
        res.put("items", timeshaftsRes);
        return new ResponseService(res);
    }

    @RequestMapping("/endTimeShaft")
    public ResponseService endTimeShaft(@RequestBody Map<String, Object> requestMap, @RequestHeader("user_id") Integer user_id) throws Exception {
        log.info("结束时间轴开始");
        Integer group_id = (Integer) requestMap.get("chatId");
        String type = (String) requestMap.get("type");
        timeShaftOp.endTimeShaft(group_id, type, user_id);
        log.info("结束时间轴成功");
        return new ResponseService();
    }

    @RequestMapping("/getTimeShaftData")
    public ResponseService getTimeShaftData(@RequestParam("start") String start_time, @RequestParam("end") String end_time) throws Exception {
        List<Map<String, Object>> res = timeShaftOp.getTimeShaftData(start_time, end_time);
        return new ResponseService(res);
    }

    @RequestMapping("/genTimeShaftFromMessages")
    public ResponseService genTimeShaftFromMessages(@RequestBody Map<String, Object> requestMap) throws Exception {
        Integer chat_id = (Integer)requestMap.get("chatId");
        Integer user_id = (Integer)requestMap.get("userId");
        String title = (String)requestMap.get("title");
        ArrayList<String> tags = (ArrayList<String>)requestMap.get("tags");
        String conclude = (String)requestMap.get("conclude");
        String type = (String)requestMap.get("type");
        ArrayList<Integer> msgIds = (ArrayList<Integer>)requestMap.get("msgIds");
        System.out.println(msgIds.size());
        timeShaftOp.genTimeShaftFromMessages(chat_id, user_id, title, tags, conclude, type, msgIds);
        return new ResponseService();
    }

    @RequestMapping("/getSingleTimeshaft")
    public ResponseService getSingleTimeshaft(@RequestHeader("user_id") Integer user_id, @RequestBody Map<String, Object> requestMap) throws Exception {
        Integer timeshaft_id = (Integer) requestMap.get("timeshaft_id");
        Map<String, Object> res = timeShaftOp.getSingleTimeshaft(user_id, timeshaft_id);
        return new ResponseService(res);
    }

    @RequestMapping("/queryTimeShaftId")
    public ResponseService queryTimeShaftId(@RequestHeader("user_id") Integer user_id, @RequestBody Map<String, Object> requestMap) throws Exception {
        String msg = (String) requestMap.get("msg");
        Map<String, Object> res = timeShaftOp.getIdByKey(msg);
        return new ResponseService(res);
    }

    @RequestMapping("/meetingChange")
    public ResponseService meetingChange(@RequestBody Map<String, Object> requestMap) {
        String type = (String) requestMap.get("type");
        Integer chatId = (Integer) requestMap.get("chatId");
        boolean isMeeting = (boolean) requestMap.get("isMeeting");
        String operation = isMeeting? onMeeting : offMeeting;
        timeShaftOp.sendNotification(type, chatId, operation);
        return new ResponseService();
    }

    @RequestMapping("/delTimeshaft")
    public ResponseService delTimeshaft(@RequestBody Map<String, Object> requestMap, @RequestHeader("user_id") Integer user_id) {
        Integer id = (Integer) requestMap.get("id");
        timeShaftOp.delTimeshaft(id, user_id);
        return new ResponseService();
    }

    @RequestMapping("/getTimeTags")
    public ResponseService getTimeTags(@RequestBody Map<String, Object> requestMap, @RequestHeader("user_id") Integer user_id) {
        Integer id = (Integer) requestMap.get("chatId");
        String type = (String) requestMap.get("type");
        ArrayList<String> res = timeShaftOp.getTimeTags(id, type, user_id);
        return new ResponseService(res);
    }

    @RequestMapping("/updateTimeNode")
    public ResponseService updateTimeNode(@RequestBody Map<String, Object> requestMap, @RequestHeader("user_id") Integer user_id) throws Exception {
        Integer id = (Integer) requestMap.get("id");
        ArrayList<String> tags = (ArrayList<String>) requestMap.get("tags");
        String type = (String) requestMap.get("type");
        String conclude = (String) requestMap.get("conclude");
        timeShaftOp.updateTimeNode(id, type, tags, conclude, user_id);
        return new ResponseService();
    }

    @RequestMapping("/searchTimeByTag")
    public ResponseService searchTimeByTag(@RequestBody Map<String, Object> requestMap, @RequestHeader("user_id") Integer user_id) throws Exception {
        log.info("获取时间轴开始");
        Integer id = (Integer) requestMap.get("chatId");
        String tag = (String) requestMap.get("tag");
        String type = (String) requestMap.get("type");
        List<Map<String, Object>> timeshaftsRes = timeShaftOp.searchTimeByTag(id, type, user_id, tag);
        log.info("获取时间轴成功");
        Map<String, Object> res = new HashMap<>();
        res.put("items", timeshaftsRes);
        return new ResponseService(res);
    }

    @RequestMapping("/updateTimeState")
    public ResponseService updateTimeState(@RequestBody Map<String, Object> requestMap, @RequestHeader("user_id") Integer user_id) throws Exception {
        Integer id = (Integer) requestMap.get("id");
        Boolean state = (Boolean) requestMap.get("state");
        timeShaftOp.updateTimeState(id, state, user_id);
        return new ResponseService();
    }

    @RequestMapping("/starTimeNode")
    public ResponseService starTimeNode(@RequestBody Map<String, Object> requestMap, @RequestHeader("user_id") Integer user_id) throws Exception {
        Integer id = (Integer) requestMap.get("id");
        timeShaftOp.starTimeNode(id, user_id);
        return new ResponseService();
    }
}
