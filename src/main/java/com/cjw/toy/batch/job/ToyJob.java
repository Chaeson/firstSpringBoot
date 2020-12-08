package com.cjw.toy.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ToyJob {

    JobRepository jobRepository;

    JobBuilderFactory jobBuilderFactory;

    StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job toyJob(){
        log.info("toyJob Start...");
        return jobBuilderFactory.get("toyJob")
            .repository(jobRepository)
            .start(firstStep())
            .build();
    }

    @Bean
    public Step firstStep(){
        log.info("firstStep Start....");
        return stepBuilderFactory.get("firstStep")
                .chunk(10)
                .build();
    }

}
