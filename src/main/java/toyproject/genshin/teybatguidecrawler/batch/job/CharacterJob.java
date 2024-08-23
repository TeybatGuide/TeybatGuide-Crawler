package toyproject.genshin.teybatguidecrawler.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import toyproject.genshin.teybatguidecrawler.character.CharactersCrawlService;
import toyproject.genshin.teybatguidecrawler.character.domain.CharacterAttributes;

import java.util.List;

@EnableBatchProcessing
@RequiredArgsConstructor
public class CharacterJob implements BatchJob {

    private static final String JOB_NAME = "characterJob";
    private static final String GROUP_NAME = "characterGroup";

    private final CharactersCrawlService charactersCrawlService;

    @Override
    public String getJobName() {
        return JOB_NAME;
    }

    @Override
    public String getGroupName() {
        return GROUP_NAME;
    }

    @Bean
    @Override
    public Job createJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(crawlCharacterStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step crawlCharacterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(JOB_NAME, jobRepository)
                .tasklet(crawlCharacterTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet crawlCharacterTasklet() {
        return (contribution, chunkContext) -> {
            List<CharacterAttributes> crawl = charactersCrawlService.crawl();
            charactersCrawlService.saveCharacterAttributes(crawl);
            return RepeatStatus.FINISHED;
        };
    }

}
