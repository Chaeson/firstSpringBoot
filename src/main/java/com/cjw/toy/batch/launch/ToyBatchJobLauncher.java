package com.cjw.toy.batch.launch;

import com.cjw.toy.batch.job.ToyJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableBatchProcessing
public class ToyBatchJobLauncher {

    JobLauncher jobLauncher;

    Job job;

    @Bean
    public void toyJobLaunch() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("time",System.currentTimeMillis())
                        .toJobParameters();
        log.info("jobParameters::{}",jobParameters.toString());
        jobLauncher.run(job,jobParameters);
    }


}
