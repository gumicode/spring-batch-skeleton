package com.example.skeleton.job;

import com.example.skeleton.global.step.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * FLOW_JOB 으로 STEP 의 순서를 지정할 수도 있지만, 반대로 실질적인 순서는 FLOW 안에서 다 지정하고, 이후 JOB 에는 FLOW 만 추가해서
 * 사용 하는 방법 도 있다. 동작 순서가 복잡 해 질 경우 이처럼 나누는 것이 가독성이 좋아진다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FlowBuilderJob {

	public static final String BEAN_NAME = "FLOW_BUILDER_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(flow())
				.end()
				.build();
	}

	private Flow flow() {
		return new FlowBuilder<Flow>("flow")
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
					.to(steps.get(PrintFiveStep.BEAN_NAME))
				.build();
	}
}
