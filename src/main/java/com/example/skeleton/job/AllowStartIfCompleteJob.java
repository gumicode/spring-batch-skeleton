package com.example.skeleton.job;

import com.example.skeleton.global.step.RuntimeExceptionStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
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
public class AllowStartIfCompleteJob {

	public static final String BEAN_NAME = "ALLOW_START_IF_COMPLETE_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(allowStartIfCompleteStep())
				.next(steps.get(RuntimeExceptionStep.BEAN_NAME))
				.build();
	}

	private Step allowStartIfCompleteStep() {
		return stepBuilderFactory.get("ALLOW_START_IF_COMPLETE_STEP")
				.tasklet((contribution, chunkContext) -> {

					log.debug("***** allowStartIfCompleteStep ");
					return RepeatStatus.FINISHED;

				})
				.allowStartIfComplete(true) // 동일한 JOB 실행시 성공 했더라도 항상 STEP 을 실행한다.
				.build();
	}
}
