package demo.sbm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@Slf4j
public class SpringBatchInMemoryConfig {
    @Bean
    public DefaultBatchConfigurer batchConfigurer() {
        return new DefaultBatchConfigurer() {

            private final JobRepository jobRepository;
            private final JobExplorer jobExplorer;
            private final JobLauncher jobLauncher;

            {

                MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean(); //NOSONAR
                try {
                    this.jobRepository = jobRepositoryFactory.getObject();
                    MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory); //NOSONAR
                    this.jobExplorer = jobExplorerFactory.getObject();
                    SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
                    simpleJobLauncher.setJobRepository(Objects.requireNonNull(jobRepository));
                    simpleJobLauncher.afterPropertiesSet();
                    this.jobLauncher = simpleJobLauncher;
                } catch (Exception e) {
                    log.error("Some exception occurred when initializing batch", e);
                    throw new IllegalStateException();
                }
            }

            @Override
            public JobRepository getJobRepository() {
                return jobRepository;
            }

            @Override
            public JobExplorer getJobExplorer() {
                return jobExplorer;
            }

            @Override
            public JobLauncher getJobLauncher() {
                return jobLauncher;
            }
        };
    }
}
