package com.mimi.batch.library.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JobRestController {

    @Autowired private JobLauncher jobLauncher;
    @Autowired private Job job;

    @GetMapping("/startJob")
    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> params = new HashMap<>();
        params.put( "time", new JobParameter( System.currentTimeMillis() ) );
        JobParameters jobParameters = new JobParameters( params );
        JobExecution jobExecution = jobLauncher.run( job, jobParameters );

        while ( jobExecution.isRunning() ) {
            System.out.println("Borrowing in progress... ");
        }

        return jobExecution.getStatus();
    }
}
