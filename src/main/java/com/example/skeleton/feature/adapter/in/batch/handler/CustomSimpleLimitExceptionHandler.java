package com.example.skeleton.feature.adapter.in.batch.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomSimpleLimitExceptionHandler {

    public static final String BEAN_NAME = "CUSTOM_SIMPLE_LIMIT_EXCEPTION_HANDLER";

    @Bean(BEAN_NAME)
    public ExceptionHandler exceptionHandler() {
        return new SimpleLimitExceptionHandler(3);
    }
}
