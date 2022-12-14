package com.example.skeleton.feature.adapter.in.batch.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component(StepExceptionHandler.BEAN_NAME)
public class StepExceptionHandler implements ExceptionHandler {

    public static final String BEAN_NAME = "STEP_EXCEPTION_HANDLER";
    @Override
    public void handleException(RepeatContext context, Throwable throwable) throws Throwable {

        log.info("step exception handler, count : {}", context.getStartedCount());

//        context.setTerminateOnly(); // 현재 배치가 비정상적으로 완료되어야 함을 프레임워크에 알립니다
//        context.setCompleteOnly(); // 현재 배치가 정상적으로 완료되어야 함을 프레임워크에 알립니다

        throw throwable;
    }
}
