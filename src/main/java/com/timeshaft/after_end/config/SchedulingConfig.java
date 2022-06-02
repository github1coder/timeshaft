package com.timeshaft.after_end.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务开关
 * @author duanjw
 */
@Configuration
//启用定时任务
@EnableScheduling
//配置文件读取是否启用此配置
@ConditionalOnProperty(prefix = "scheduling", name = "enabled", havingValue = "true")
public class SchedulingConfig {
}
