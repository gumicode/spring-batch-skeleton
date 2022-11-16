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
 * 하나의 JOB 내에서 동일한 STEP 을 여러번 실행할 경우 최대 실행 가능한 개수를 지정할 수 있다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StartLimitJob {

	public static final String BEAN_NAME = "TASKLET_START_LIMIT_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(startLimitStep())
				.next(startLimitStep())
				.next(startLimitStep())
				.build();
	}

	private Step startLimitStep() {
		return stepBuilderFactory.get("START_LIMIT_STEP")
				.tasklet((contribution, chunkContext) -> {

					log.debug("***** startLimitStep ");
					return RepeatStatus.FINISHED;

				})
				.startLimit(2) // 설정한 최대 한도까지만 동작하고 이상 실행 할 경우 StartLimitExceededException 예외 발생
				.build();
	}
}
