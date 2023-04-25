package com.example.skeleton.product.adapter.in.batch;

import com.example.skeleton.product.adapter.in.batch.listener.PrintCountdownJobExecutionListener;
import com.example.skeleton.product.adapter.in.batch.step.ProductSaveRandomJdbcStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 *  JDBC Batch ItemWriter 로 저장
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductSaveRandomJdbcJob {

    public static final String BEAN_NAME = "PRODUCT_SAVE_RANDOM_JDBC_JOB";
    private final JobBuilderFactory jobBuilderFactory;
    private final Map<String, Step> steps;
    private final Map<String, JobExecutionListener> jobListeners;

    @Bean(BEAN_NAME)
    public Job job() {
        return jobBuilderFactory.get(BEAN_NAME)
                .listener(jobListeners.get(PrintCountdownJobExecutionListener.BEAN_NAME))
                .start(steps.get(ProductSaveRandomJdbcStep.BEAN_NAME))
                .build();
    }
}
