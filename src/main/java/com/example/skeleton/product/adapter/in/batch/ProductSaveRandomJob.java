package com.example.skeleton.product.adapter.in.batch;

import com.example.skeleton.product.adapter.in.batch.step.ProductSaveRandomStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
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
public class ProductSaveRandomJob {

    public static final String BEAN_NAME = "PRODUCT_SAVE_RANDOM_JOB";
    private final JobBuilderFactory jobBuilderFactory;
    private final Map<String, Step> steps;

    @Bean(BEAN_NAME)
    public Job job() {
        return jobBuilderFactory.get(BEAN_NAME)
                .start(steps.get(ProductSaveRandomStep.BEAN_NAME))
                .build();
    }
}
