package com.example.skeleton.product.adapter.in.batch;

import com.example.skeleton.product.adapter.in.batch.step.ProductSaveRandomStep2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 *  JPA ItemWriter 로 저장
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductSaveRandomJob2 {

    public static final String BEAN_NAME = "PRODUCT_SAVE_RANDOM_JOB2";
    private final JobBuilderFactory jobBuilderFactory;
    private final Map<String, Step> steps;

    @Bean(BEAN_NAME)
    public Job job() {
        return jobBuilderFactory.get(BEAN_NAME)
                .start(steps.get(ProductSaveRandomStep2.BEAN_NAME))
                .build();
    }
}
