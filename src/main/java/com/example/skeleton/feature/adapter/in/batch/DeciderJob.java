package com.example.skeleton.feature.adapter.in.batch;

import com.example.skeleton.feature.adapter.in.batch.step.decider.EvenOddDecider;
import com.example.skeleton.feature.adapter.in.batch.step.PrintOneStep;
import com.example.skeleton.feature.adapter.in.batch.step.PrintTwoStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * FLOW 를 구성하기 복잡하다면 Decider 를 사용해볼 수 있다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeciderJob {

	public static final String BEAN_NAME = "DECIDER_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final Map<String, Step> steps;
	private final Map<String, JobExecutionDecider> deciders;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(steps.get(PrintOneStep.BEAN_NAME)) // decider 는 start 에 올 수 없다. ExitStatus 가 COMPLETED 가 아니면 아래로 넘어가지 않음
				.next(deciders.get(EvenOddDecider.BEAN_NAME))
				.from(deciders.get(EvenOddDecider.BEAN_NAME))
					.on("ODD")
					.to(steps.get(PrintTwoStep.BEAN_NAME))
				.from(deciders.get(EvenOddDecider.BEAN_NAME))
					.on("EVEN")
					.to(steps.get(PrintTwoStep.BEAN_NAME))
				.end()
				.build();
	}
}
