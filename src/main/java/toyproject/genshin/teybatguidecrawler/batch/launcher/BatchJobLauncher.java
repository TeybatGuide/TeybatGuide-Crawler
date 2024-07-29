package toyproject.genshin.teybatguidecrawler.batch.launcher;

import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchJobLauncher extends QuartzJobBean {

    private final JobLauncher jobLauncher;
    private final ApplicationContext context;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            // Quartz JobDataMap에서 Spring Batch Job 이름 가져오기
            String jobName = context.getJobDetail().getJobDataMap().getString("jobName");

            // Spring Batch Job 실행
            Job job = (Job) this.context.getBean(jobName);
            JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }

    }
}
