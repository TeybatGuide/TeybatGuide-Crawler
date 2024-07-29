package toyproject.genshin.teybatguidecrawler.batch.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import toyproject.genshin.teybatguidecrawler.batch.launcher.BatchJobLauncher;

import java.util.List;

@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        return new SchedulerFactoryBean();
    }

    @Bean
    public Scheduler scheduler(
            SchedulerFactoryBean schedulerFactoryBean, List<JobDetail> jobDetails, List<Trigger> triggers
    ) throws SchedulerException {

        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        for (JobDetail jobDetail : jobDetails) {
            if (!scheduler.checkExists(jobDetail.getKey())) {
                scheduler.addJob(jobDetail, true);
            }
        }

        for (Trigger trigger : triggers) {
            if (!scheduler.checkExists(trigger.getKey())) {
                scheduler.scheduleJob(trigger);
            }
        }

        scheduler.start();
        return scheduler;
    }

}
