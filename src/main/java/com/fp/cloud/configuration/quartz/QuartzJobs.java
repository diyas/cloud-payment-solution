package com.fp.cloud.configuration.quartz;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

//@Configuration
public class QuartzJobs {
    private static final String CRON_EVERY_FIVE_MINUTES = "0 0/5 * ? * * *";

//    @Bean(name = "memberStats")
//    public JobDetailFactoryBean jobMemberStats() {
//        return QuartzConfiguration.createJobDetail(MemberStatsJob.class, "Member Statistics Job");
//    }

//    @Bean(name = "memberStatsTrigger")
//    public SimpleTriggerFactoryBean triggerMemberStats(@Qualifier("memberStats") JobDetail jobDetail) {
//        return QuartzConfiguration.createTrigger(jobDetail, 60000, "Member Statistics Trigger");
//    }

//    @Bean(name = "memberClassStats")
//    public JobDetailFactoryBean jobMemberClassStats() {
//        return QuartzConfiguration.createJobDetail(MemberClassStatsJob.class, "Class Statistics Job");
//    }

//    @Bean(name = "memberClassStatsTrigger")
//    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("memberClassStats") JobDetail jobDetail) {
//        return QuartzConfiguration.createCronTrigger(jobDetail, CRON_EVERY_FIVE_MINUTES, "Class Statistics Trigger");
//    }
}

