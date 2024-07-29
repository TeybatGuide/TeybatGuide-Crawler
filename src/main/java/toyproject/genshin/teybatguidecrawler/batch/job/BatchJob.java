package toyproject.genshin.teybatguidecrawler.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.transaction.PlatformTransactionManager;

public interface BatchJob {

    String getJobName();

    String getGroupName();

    Job createJob(JobRepository jobRepository, PlatformTransactionManager transactionManager);
}
