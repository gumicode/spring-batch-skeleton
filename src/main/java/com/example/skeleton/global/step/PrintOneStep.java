package com.example.skeleton.global.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PrintOneStep {

	public static final String BEAN_NAME = "PRINT_ONE_STEP";
	private final StepBuilderFactory stepBuilderFactory;

	@Bean(BEAN_NAME)
	public Step step() {
		return stepBuilderFactory.get(BEAN_NAME)
				.tasklet((contribution, chunkContext) -> {

					log.debug("***** printOneStep : 1");
					contribution.setExitStatus(ExitStatus.NOOP);

					return RepeatStatus.FINISHED;
				})
				.build();
	}
}
