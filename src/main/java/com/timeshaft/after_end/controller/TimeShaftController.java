package com.timeshaft.after_end.controller;

import com.timeshaft.after_end.service.ResponseService;
import com.timeshaft.after_end.service.timeshaftOp.TimeShaftOp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseService getTimeshaft(@RequestBody Map<String, Object> requestMap) throws Exception {
        log.info("获取时间轴开始");
        Integer group_id = (Integer) requestMap.get("group_id");
        String type = (String) requestMap.get("type");
        List<Map<String, Object>> timeshaftsRes = timeShaftOp.getTimeshaft(group_id, type);
        log.info("获取时间轴成功");
        Map<String, Object> res = new HashMap<>();
        res.put("items", timeshaftsRes);
        return new ResponseService(res);
    }

    @RequestMapping("/endTimeShaft")
    public ResponseService endTimeShaft(@RequestBody Map<String, Object> requestMap) throws Exception {
        log.info("结束时间轴开始");
        Integer group_id = (Integer) requestMap.get("group_id");
        String type = (String) requestMap.get("type");
        timeShaftOp.endTimeShaft(group_id, type);
        log.info("结束时间轴成功");
        return new ResponseService();
    }
}
