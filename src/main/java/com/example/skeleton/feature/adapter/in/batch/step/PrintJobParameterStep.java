package com.example.skeleton.feature.adapter.in.batch.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PrintJobParameterStep {

	public static final String BEAN_NAME = "PRINT_JOB_PARAMETER_STEP";
	private final StepBuilderFactory stepBuilderFactory;

	@Bean(BEAN_NAME)
	@JobScope
	public Step step() {
		return stepBuilderFactory.get(BEAN_NAME)
				.tasklet((contribution, chunkContext) -> {

					Map<String, JobParameter> jobParameterMaps = contribution.getStepExecution().getJobExecution().getJobParameters().getParameters();
					for (String key : jobParameterMaps.keySet()) {
						log.debug("***** key : {}, value : {}", key, jobParameterMaps.get(key));
					}
					return RepeatStatus.FINISHED;

				})
				.build();
	}
}
