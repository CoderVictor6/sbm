package demo.sbm.config;

import demo.sbm.entity.User;
import demo.sbm.entity.User1;
import demo.sbm.entity.User2;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   @Qualifier("step1") Step step1,
                   @Qualifier("step2") Step step2,
                   JobExecutionListener listener) {
        return jobBuilderFactory.get("job")
                .start(step1)
                .next(step2)
                .listener(listener)
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory,
                      @Qualifier("u1Reader") ItemReader<User1> u1Reader,
                      @Qualifier("u1Processor") ItemProcessor<User1, User> u1Processor,
                      @Qualifier("u1Writer") ItemWriter<User> u1Writer) {
        return stepBuilderFactory.get("step1")
                .<User1, User>chunk(500)
                .reader(u1Reader)
                .processor(u1Processor)
                .writer(u1Writer)
                .build();
    }

    @Bean
    public Step step2(StepBuilderFactory stepBuilderFactory,
                      @Qualifier("u2Reader") ItemReader<User2> u2Reader,
                      @Qualifier("u2Processor") ItemProcessor<User2, User> u2Processor,
                      @Qualifier("u2Writer") ItemWriter<User> u2Writer) {
        return stepBuilderFactory.get("step2")
                .<User2, User>chunk(500)
                .reader(u2Reader)
                .processor(u2Processor)
                .writer(u2Writer)
                .build();
    }

    @Bean
    public MyBatisCursorItemReader<User1> u1Reader(SqlSessionFactory ssf) {
        return new MyBatisCursorItemReaderBuilder<User1>()
                .sqlSessionFactory(ssf)
                .queryId("demo.sbm.mapper.User1Mapper.findAll")
                .build();
    }

    @Bean
    public MyBatisBatchItemWriter<User> u1Writer(@Qualifier("batchSsf") SqlSessionFactory ssf) {
        return new MyBatisBatchItemWriterBuilder<User>()
                .sqlSessionFactory(ssf)
                .statementId("demo.sbm.mapper.UserMapper.updateByPrimaryKey")
                .build();
    }

    @Bean
    public MyBatisCursorItemReader<User2> u2Reader(SqlSessionFactory ssf) {
        return new MyBatisCursorItemReaderBuilder<User2>()
                .sqlSessionFactory(ssf)
                .queryId("demo.sbm.mapper.User2Mapper.findAll")
                .build();
    }

    @Bean
    public MyBatisBatchItemWriter<User> u2Writer(@Qualifier("batchSsf") SqlSessionFactory ssf) {
        return new MyBatisBatchItemWriterBuilder<User>()
                .sqlSessionFactory(ssf)
                .statementId("demo.sbm.mapper.UserMapper.updateByPrimaryKey")
                .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                BatchStatus status = jobExecution.getStatus();
                log.info("beforeJob! status is {}", status);
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                BatchStatus status = jobExecution.getStatus();
                log.info("afterJob! status is {}", status);
            }
        };
    }
}
