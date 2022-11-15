package com.example.skeleton.job;

import com.example.skeleton.global.step.PrintJobParameterStep;
import com.example.skeleton.global.validator.NameJobParametersValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * JOB PARAMETER 에 대한 유효성 검사를 할 수 있다.
 * STEP VALIDATOR 는 존재하지 않는다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ValidatorJob {

	public static final String BEAN_NAME = "NAME_VALIDATOR_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(steps.get(PrintJobParameterStep.BEAN_NAME))
				.validator(new NameJobParametersValidator())
				.build();
	}
}
