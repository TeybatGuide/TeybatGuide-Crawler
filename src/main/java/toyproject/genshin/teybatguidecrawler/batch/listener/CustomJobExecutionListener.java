package toyproject.genshin.teybatguidecrawler.batch.listener;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class CustomJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(@NotNull JobExecution jobExecution) {
        System.out.println("job name : " + jobExecution.getJobInstance().getJobName() + " start");
    }

    @Override
    public void afterJob(@NotNull JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        long startTime = getMilli(Objects.requireNonNull(jobExecution.getStartTime()));
        long endTime = getMilli(Objects.requireNonNull(jobExecution.getEndTime()));
        long executionTime = endTime - startTime;
        System.out.println("job name : " + jobName + " end " + " execution time : " + executionTime);
    }

    private long getMilli(@NotNull LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
