package com.example.skeleton.product.adapter.in.batch.step;

import com.example.skeleton.product.adapter.in.batch.ProductSaveRandomJdbcJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * ProductSaveRandomJdbcJob 을 실행하기 위한 STEP
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductSaveRandomJdbcJobStep {

    public static final String BEAN_NAME = "PRODUCT_SAVE_RANDOM_JDBC_JOB_STEP";
    private final StepBuilderFactory stepBuilderFactory;
    private final Map<String, Job> jobs;
    private final JobLauncher jobLauncher;

    @Bean(BEAN_NAME)
    @JobScope
    public Step step() {
        return stepBuilderFactory.get(BEAN_NAME)
                .job(jobs.get(ProductSaveRandomJdbcJob.BEAN_NAME))
                .launcher(jobLauncher)
                .build();
    }
}
