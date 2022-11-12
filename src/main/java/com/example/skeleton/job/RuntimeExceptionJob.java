package com.example.skeleton.job;

import com.example.skeleton.global.step.PrintJobParameterStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RuntimeExceptionJob {

	public static final String BEAN_NAME = "RUNTIME_EXCEPTION_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(steps.get(PrintJobParameterStep.BEAN_NAME))
				.next(runtimeExceptionStep())
				.build();
	}

	private Step runtimeExceptionStep() {
		return stepBuilderFactory.get("RUNTIME_EXCEPTION_STEP")
				.tasklet((contribution, chunkContext) -> {
					throw new RuntimeException();
				})
				.build();
	}
}
