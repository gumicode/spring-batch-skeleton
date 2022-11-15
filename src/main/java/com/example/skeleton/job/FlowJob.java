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

/**
 * FLOW_JOB 으로 STEP 의 동작 순서를 지정할 수 있다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FlowJob {

	public static final String BEAN_NAME = "FLOW_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(steps.get(PrintOneStep.BEAN_NAME))
					.on("COMPLETED")
					.to(steps.get(PrintTwoStep.BEAN_NAME))
				.from(steps.get(PrintOneStep.BEAN_NAME)) // from 의 경우엔 다시 on 전의 STEP 을 명시해주어야 한다.
					.on("COMPLETED")
					.to(steps.get(PrintThreeStep.BEAN_NAME)) // 이미 위에 조건이 앞에서 동일한 조건이 있어서 COMPLETED 라도 실행되지 않는다.
				.from(steps.get(PrintOneStep.BEAN_NAME))
					.on("FAILED")
					.to(steps.get(PrintFourStep.BEAN_NAME))
				.from(steps.get(PrintOneStep.BEAN_NAME))
					.on("*")
				.to(steps.get(PrintFiveStep.BEAN_NAME)) // 위에서 단 하나라도 실행되지 않았을 경우에만 실행 된다.
				.end()
				.build();
	}
}
