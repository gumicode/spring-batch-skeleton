package com.example.skeleton.feature.adapter.in.batch;

import com.example.skeleton.feature.adapter.in.batch.step.PrintJobParameterStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * RepeatTemplate Composite
 * CompositeCompletionPolicy 를 이용하여 or 조건으로 SimpleCompletionPolicy 또는 TimeoutTerminationPolicy 중 먼저 조건에
 * 부합한다면 반복을 종료한다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RepeatTemplateCompletionPolicyCompositeJob {

	public static final String BEAN_NAME = "REPEAT_TEMPLATE_COMPLETION_POLICY_COMPOSITE_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(repeatTemplateStep())
				.next(steps.get(PrintJobParameterStep.BEAN_NAME))
				.build();
	}

	private Step repeatTemplateStep() {
		return stepBuilderFactory.get("PRINT_STEP")
				.tasklet((contribution, chunkContext) -> {

					log.debug("***** repeatTemplateStep");

					RepeatTemplate repeatTemplate = new RepeatTemplate();
					CompletionPolicy[] completionPolicies = new CompletionPolicy[]{new SimpleCompletionPolicy(3), new TimeoutTerminationPolicy(1)};
					CompositeCompletionPolicy compositeCompletionPolicy = new CompositeCompletionPolicy();
					compositeCompletionPolicy.setPolicies(completionPolicies);
					repeatTemplate.setCompletionPolicy(compositeCompletionPolicy);

					repeatTemplate.iterate(context -> {
						// 아마도 1ms 보다 chunk size 3 이 먼저 끝날 것이므로 3번 호출하고 종료될 가능성이 높다.
						log.info("***** repeat, composite count {}", context.getStartedCount());
						return RepeatStatus.CONTINUABLE;
					});

					return RepeatStatus.FINISHED;

				})
				.build();
	}
}
