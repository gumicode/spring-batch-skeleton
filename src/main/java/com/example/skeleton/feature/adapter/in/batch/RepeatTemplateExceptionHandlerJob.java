package com.example.skeleton.feature.adapter.in.batch;

import com.example.skeleton.feature.adapter.in.batch.handler.CustomSimpleLimitExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * RepeatTemplate
 * - SimpleLimitExceptionHandler : 정의한 size 까지 exception 이 발생 하더라도 건너뛴다. 사이즈가 3이면 총 3번을 건너뛴다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RepeatTemplateExceptionHandlerJob {

    public static final String BEAN_NAME = "REPEAT_TEMPLATE_EXCEPTION_HANDLER_JOB";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Map<String, ExceptionHandler> exceptionHandlers;


    @Bean(BEAN_NAME)
    public Job job() {
        return jobBuilderFactory.get(BEAN_NAME)
                .start(simpleLimitExceptionHandlerStep())
                .build();
    }

    private Step simpleLimitExceptionHandlerStep() {
        return stepBuilderFactory.get("SIMPLE_LIMIT_EXCEPTION_HANDLER_STEP")
                .tasklet((contribution, chunkContext) -> {

                    log.debug("***** simpleLimitExceptionHandlerStep");

                    RepeatTemplate repeatTemplate = new RepeatTemplate();
                    repeatTemplate.setExceptionHandler(exceptionHandlers.get(CustomSimpleLimitExceptionHandler.BEAN_NAME));

                    repeatTemplate.iterate(context -> {
                        log.info("***** repeat, count {}", context.getStartedCount());
                        throw new RuntimeException("simpleLimitExceptionHandlerStep RuntimeException");
                    });

                    return RepeatStatus.FINISHED;

                })
                .build();
    }
}
