package com.example.skeleton.feature.adapter.in.batch;

import com.example.skeleton.feature.adapter.in.batch.step.PrintOneStep;
import com.example.skeleton.feature.adapter.in.batch.step.PrintTwoStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 내가 직접 정의한 EXIT_STATUS 를 사용할 수 있다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomExitStatusJob {

	public static final String BEAN_NAME = "CUSTOM_EXIT_STATUS_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(customExitStatusStep())
					.on("PASS")
					.to(steps.get(PrintOneStep.BEAN_NAME))
				.from(customExitStatusStep())
					.on("*")
					.to(steps.get(PrintTwoStep.BEAN_NAME))
				.end()
				.build();
	}

	private Step customExitStatusStep() {
		return stepBuilderFactory.get("CUSTOM_EXIT_STATUS_STEP")
				.tasklet((contribution, chunkContext) -> {

					log.debug("***** customExitStatusStep");
					contribution.setExitStatus(new ExitStatus("PASS"));
					return RepeatStatus.FINISHED;

				})
				.build();
	}
}
