package com.example.skeleton.feature.adapter.in.batch;

import com.example.skeleton.common.incrementer.UniqueRunIdIncrementer;
import com.example.skeleton.feature.adapter.in.batch.step.PrintJobParameterStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 동일한 파라미터가 들어 오더라도 무한 반복 실행할 수 있는 옵션이다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class UniqueIncrementerJob {

	public static final String BEAN_NAME = "UNIQUE_INCREMENTER_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(steps.get(PrintJobParameterStep.BEAN_NAME))
				.incrementer(new UniqueRunIdIncrementer()) // 중복 실행 가능 옵션, 단 직접 JobLauncher 실행시 동작하지 않음
				.build();
	}
}
