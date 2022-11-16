package com.example.skeleton.feature.adapter.in.batch.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PrintTwoStep {

	public static final String BEAN_NAME = "PRINT_TWO_STEP";
	private final StepBuilderFactory stepBuilderFactory;

	@Bean(BEAN_NAME)
	public Step step() {
		return stepBuilderFactory.get(BEAN_NAME)
				.tasklet((contribution, chunkContext) -> {
					log.debug("***** printTwoStep : 2");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
}
