package com.example.skeleton.feature.adapter.in.batch;

import com.example.skeleton.feature.adapter.in.batch.handler.StepExceptionHandler;
import com.example.skeleton.feature.adapter.in.batch.step.RuntimeExceptionStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * ExceptionHandlerJob
 * Exception 이 발생 했을때 핸들링을 한다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ExceptionHandlerJob {

    public static final String BEAN_NAME = "EXCEPTION_HANDLER_JOB";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Map<String, Step> steps;
    private final Map<String, ExceptionHandler> exceptionHandlers;


    @Bean(BEAN_NAME)
    public Job job() {
        return jobBuilderFactory.get(BEAN_NAME)
                .start(exceptionHandlerStep())
                .next(steps.get(RuntimeExceptionStep.BEAN_NAME))
                .build();
    }

    private Step exceptionHandlerStep() {
        return stepBuilderFactory.get("EXCEPTION_HANDLER_STEP")
                .tasklet((contribution, chunkContext) -> {

                    log.debug("***** exceptionHandlerStep");

                    throw new RuntimeException("exceptionHandlerStep RuntimeException");

                })
                .exceptionHandler(exceptionHandlers.get(StepExceptionHandler.BEAN_NAME))
                .build();
    }
}
