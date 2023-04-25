package com.example.skeleton.feature.adapter.in.batch.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PrintExecutionContextStep {

	public static final String BEAN_NAME = "PRINT_EXECUTION_CONTEXT_STEP";
	private final StepBuilderFactory stepBuilderFactory;

	@Bean(BEAN_NAME)
	@JobScope
	public Step step() {
		return stepBuilderFactory.get(BEAN_NAME)
				.tasklet((contribution, chunkContext) -> {

					ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
					ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

					String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
					String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

					log.debug("***** jobName : {}", jobName);
					log.debug("***** stepName : {}", stepName);

					printExecutionContext(jobExecutionContext);
					printExecutionContext(stepExecutionContext);

					return RepeatStatus.FINISHED;

				})
				.build();
	}


	private void printExecutionContext(ExecutionContext executionContext) {
		if (executionContext.isEmpty()) {
			return;
		}

		List<String> keys = executionContext.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toUnmodifiableList());
		for (String key : keys) {
			Object value = executionContext.get(key);
			log.debug("***** key : {}, value : {}", key, value);
		}
	}
}
