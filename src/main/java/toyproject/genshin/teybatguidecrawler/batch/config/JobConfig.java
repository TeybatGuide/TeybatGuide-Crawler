package toyproject.genshin.teybatguidecrawler.batch.config;

import lombok.RequiredArgsConstructor;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toyproject.genshin.teybatguidecrawler.batch.job.BatchJob;
import toyproject.genshin.teybatguidecrawler.batch.launcher.BatchJobLauncher;

import java.util.List;
import java.util.stream.Collectors;

import static org.quartz.JobBuilder.newJob;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final List<BatchJob> jobs;

    @Bean
    public List<JobDetail> jobDetails() {
        return jobs.stream()
                .map(job -> newJob(BatchJobLauncher.class)
                        .withIdentity(job.getJobName(), job.getGroupName())
                        .usingJobData("jobName", job.getJobName())
                        .storeDurably()
                        .build())
                .collect(Collectors.toList());
    }

    @Bean
    public List<Trigger> jobTriggers(List<JobDetail> jobDetails) {
        return jobs.stream()
                .map(job -> createJobTrigger(job, jobDetails))
                .collect(Collectors.toList());
    }

    private Trigger createJobTrigger(BatchJob job, List<JobDetail> jobDetails) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName() + "Trigger", job.getGroupName())
                .forJob(getJobDetail(job, jobDetails))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(60)
                        .repeatForever())
                .build();
    }

    private JobDetail getJobDetail(BatchJob job, List<JobDetail> jobDetails) {
        return jobDetails.stream()
                .filter(jobDetail -> jobDetail.getKey().getName().equals(job.getJobName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No job detail found for job: " + job.getJobName()));
    }

}
