package com.example.skeleton.feature.adapter.in.batch;

import com.example.skeleton.feature.adapter.in.batch.listener.PrintJobExecutionListener;
import com.example.skeleton.feature.adapter.in.batch.step.listener.PrintStepExecutionListener;
import com.example.skeleton.feature.adapter.in.batch.step.PrintJobParameterStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * JOB_LISTENER , STEP_LISTENER 를 지정할 수 있다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ListenerJob {

	public static final String BEAN_NAME = "PRINT_LISTENER_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Map<String, Step> steps;
	private final Map<String, JobExecutionListener> jobListeners;
	private final Map<String, StepExecutionListener> stetListeners;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.listener(jobListeners.get(PrintJobExecutionListener.BEAN_NAME))
				.start(steps.get(PrintJobParameterStep.BEAN_NAME))
				.next(printListenerStep())
				.build();
	}

	private Step printListenerStep() {
		return stepBuilderFactory.get("PRINT_LISTENER_STEP")
				.listener(stetListeners.get(PrintStepExecutionListener.BEAN_NAME))
				.tasklet((contribution, chunkContext) -> {
					log.debug("***** printListenerStep");
					return RepeatStatus.FINISHED;
				})
				.build();
	}
}
