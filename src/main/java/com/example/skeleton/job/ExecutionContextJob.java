package com.example.skeleton.job;

import com.example.skeleton.global.step.PrintExecutionContextStep;
import com.example.skeleton.global.step.PrintJobParameterStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * EXECUTION_CONTEXT 정보를 출력한다.
 * JOB_EXECUTION_CONTEXT 에는 JOB_PARAMETER 정보가 포함되어 있다.
 * 내가 직접 값을 넣을수도 있다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ExecutionContextJob {

	public static final String BEAN_NAME = "PRINT_EXECUTION_CONTEXT_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(steps.get(PrintJobParameterStep.BEAN_NAME))
				.next(steps.get(PrintExecutionContextStep.BEAN_NAME))
				.build();
	}
}
