package toyproject.genshin.teybatguidecrawler.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import toyproject.genshin.teybatguidecrawler.batch.launcher.BatchJobLauncher;
import toyproject.genshin.teybatguidecrawler.character.CharactersCrawlService;
import toyproject.genshin.teybatguidecrawler.character.domain.CharacterAttributes;

import java.util.List;

import static org.quartz.JobBuilder.newJob;

//ToDo
//  나중에 삭제할 것
@Log4j2
//@Configuration
@RequiredArgsConstructor
public class CrawlerJobConfig extends DefaultBatchConfiguration {

    private final CharactersCrawlService charactersCrawlService;

    @Bean
    public Job crawlCharacterJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("characterJob", jobRepository)
                .start(crawlCharacterStep(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step crawlCharacterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("characterJob", jobRepository)
                .tasklet(crawlCharacterTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet crawlCharacterTasklet() {
        return (contribution, chunkContext) -> {
            List<CharacterAttributes> crawl = charactersCrawlService.crawl();
            //코드 추가하기
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public JobDetail characterJobDetail() {
        return newJob(BatchJobLauncher.class)
                .withIdentity("characterJob", "crawlJobGroup")
                .usingJobData("jobName", "characterJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger characterJobTrigger() {
        return TriggerBuilder.newTrigger()
                .withIdentity("characterJobTrigger", "crawlJobGroup")
                .forJob(characterJobDetail())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(60)
                        .repeatForever())
                .build();
    }

}
