package toyproject.genshin.teybatguidecrawler.batch.config;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.quartz.*;
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

    private Trigger createJobTrigger(@NotNull BatchJob job, List<JobDetail> jobDetails) {
        return TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName() + "Trigger", job.getGroupName())
                .forJob(getJobDetail(job, jobDetails))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?")) //매일 자정에 실행
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(60)      //60초 간격으로 반복 실행
//                        .repeatForever())               //무한 반복
                .build();
    }

    private JobDetail getJobDetail(BatchJob job, @NotNull List<JobDetail> jobDetails) {
        return jobDetails.stream()
                .filter(jobDetail -> jobDetail.getKey().getName().equals(job.getJobName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No job detail found for job: " + job.getJobName()));
    }

}
