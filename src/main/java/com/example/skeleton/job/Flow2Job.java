package com.example.skeleton.job;

import com.example.skeleton.global.step.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class Flow2Job {

	public static final String BEAN_NAME = "FLOW2_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(steps.get(PrintOneStep.BEAN_NAME))
					.on("COMPLETED")
					.to(steps.get(PrintTwoStep.BEAN_NAME))
						.on("*")
						.stop()
				.from(steps.get(PrintOneStep.BEAN_NAME))
					.on("FAILED")
					.to(steps.get(PrintThreeStep.BEAN_NAME))
						.on("*")
						.stop()
				.from(steps.get(PrintOneStep.BEAN_NAME))
					.on("*")
					.to(steps.get(PrintFourStep.BEAN_NAME))
				.end()
				.build();
	}
}
