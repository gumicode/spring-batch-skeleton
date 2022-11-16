package com.example.skeleton.feature.adapter.in.batch;

import com.example.skeleton.feature.adapter.in.batch.step.PrintJobParameterStep;
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
public class PrintJob {

	public static final String BEAN_NAME = "PRINT_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(printStep())
				.next(steps.get(PrintJobParameterStep.BEAN_NAME))
				.build();
	}

	private Step printStep() {
		return stepBuilderFactory.get("PRINT_STEP")
				.tasklet((contribution, chunkContext) -> {

					log.debug("***** printStep");
					return RepeatStatus.FINISHED;

				})
				.build();
	}
}
