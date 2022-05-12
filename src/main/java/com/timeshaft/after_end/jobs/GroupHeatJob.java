package com.timeshaft.after_end.jobs;

import com.timeshaft.after_end.entity.GroupHeat;
import com.timeshaft.after_end.service.GroupHeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class GroupHeatJob {
    @Autowired
    private GroupHeatService groupHeatService;

    @Scheduled(fixedRate = 10*60*1000)
    public void changeGroupHeat() {
        log.info("群热度相关服务开始");
        List<GroupHeat> groupHeats = groupHeatService.queryAll(new GroupHeat(null, null, null, null));
        for (GroupHeat groupHeat : groupHeats) {
            groupHeat.changeGroupHeat();
            Integer groupHeatPercent = getGroupHeatPercent(groupHeat.getGroupHeat());
            log.info(String.valueOf(groupHeatPercent));
        }
        log.info("群热度相关服务结束");
    }

    private Integer getGroupHeatPercent(Integer groupHeat) {
        if (groupHeat > 100) {
            return 100;
        }
        else {
            return groupHeat / 100;
        }
    }
}
