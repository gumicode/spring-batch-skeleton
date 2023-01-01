package com.example.skeleton;

import com.example.skeleton.product.adapter.in.batch.*;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Map<String, Job> jobs;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder(); // incrementer 동작을 위해 추가
        jobParametersBuilder.addLong("long", 0L);
        jobParametersBuilder.addDouble("double", 0.0);
        jobParametersBuilder.addDate("date", new Date());
        jobParametersBuilder.addString("string", UUID.randomUUID().toString());
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();

        jobLauncher.run(jobs.get(ProductSaveRandomJob2.BEAN_NAME), jobParameters);
    }
}
