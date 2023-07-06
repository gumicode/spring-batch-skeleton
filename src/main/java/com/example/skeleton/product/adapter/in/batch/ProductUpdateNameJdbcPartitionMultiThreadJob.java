package com.example.skeleton.product.adapter.in.batch;

import com.example.skeleton.product.adapter.in.batch.listener.PrintCountdownJobExecutionListener;
import com.example.skeleton.product.adapter.in.batch.step.ProductSaveRandomJdbcJobStep;
import com.example.skeleton.product.adapter.in.batch.step.ProductUpdateNameJdbcPartitionMultiThreadStep;
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
 * 파티션으로 멀티쓰레드 STEP 으로 JDBC Batch write 하는 JOB
 * - STEP1. 랜덤으로 ProductEntity 를 만들어서 DB 에 넣는다.
 * - STEP2. DB 에서 병렬로 읽어들어 NAME 을 수정하여 JDBC 로 업데이트 한다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductUpdateNameJdbcPartitionMultiThreadJob {

    public static final String BEAN_NAME = "PRODUCT_SAVE_RANDOM_JDBC_PARTITION_MULTI_THREAD_JOB";
    private final JobBuilderFactory jobBuilderFactory;
    private final Map<String, Step> steps;
    private final Map<String, JobExecutionListener> jobListeners;

    @Bean(BEAN_NAME)
    public Job job() {
        return jobBuilderFactory.get(BEAN_NAME)
                .listener(jobListeners.get(PrintCountdownJobExecutionListener.BEAN_NAME))
                .start(steps.get(ProductSaveRandomJdbcJobStep.BEAN_NAME))
                .next(steps.get(ProductUpdateNameJdbcPartitionMultiThreadStep.BEAN_NAME))
                .build();
    }
}
