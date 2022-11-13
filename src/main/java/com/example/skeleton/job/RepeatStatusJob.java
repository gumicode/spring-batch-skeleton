package com.example.skeleton.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RepeatStatusJob {

	public static final String BEAN_NAME = "REPEAT_STATUS_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(repeatStatusStep())
				.build();
	}

	private Step repeatStatusStep() {
		return stepBuilderFactory.get("REPEAT_STATUS_STEP")
				.tasklet((contribution, chunkContext) -> {

					log.debug("***** repeatStatusStep");

					return RepeatStatus.CONTINUABLE; // FINISHED 가 호출 될 떄 까지 반복한다.

				})
				.build();
	}
}
