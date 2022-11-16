package com.example.skeleton.feature.adapter.in.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RepeatStatus 값이 FINISHED 가 나오기 전 까지 반복한다.
 * CONTINUABLE 일 경우 무한 재실행 한다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RepeatStatusJob {

	public static final String BEAN_NAME = "REPEAT_STATUS_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;


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
