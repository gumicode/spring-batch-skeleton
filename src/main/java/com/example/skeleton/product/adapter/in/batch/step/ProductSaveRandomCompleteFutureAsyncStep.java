package com.example.skeleton.product.adapter.in.batch.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 랜덤으로 ProductEntity 를 생성하여 CompletableFuture 를 사용하여 저장 하는 Step
 * Chunk 기반 멀티쓰레드 방식과 성능 차이가 어느정도 되는지 확인하기 위하여 사용
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductSaveRandomCompleteFutureAsyncStep {

    public static final String BEAN_NAME = "PRODUCT_SAVE_RANDOM_COMPLETE_FUTURE_ASYNC_STEP";
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(BEAN_NAME)
    @JobScope
    public Step step() {
        return stepBuilderFactory.get(BEAN_NAME)
                .tasklet((contribution, chunkContext) -> {



                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
