package com.example.skeleton.job;

import com.example.skeleton.global.step.PrintExecutionContextStep;
import com.example.skeleton.global.step.PrintJobParameterStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * BATCH_STATUS = 현재 배치 상태를 나타낸다. 배치 시작시, 종료시 값이 바뀐다.
 * EXIT_STATS = 배치가 죄종적으로 끝났을 때 종료 상태를 의미한다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ExitStatusJob {

	public static final String BEAN_NAME = "FAILED_STEP_EXECUTION_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(steps.get(PrintJobParameterStep.BEAN_NAME))
				.next(failedStepExecutionStep())
				.next(steps.get(PrintExecutionContextStep.BEAN_NAME))
				.build();
	}

	private Step failedStepExecutionStep() {
		return stepBuilderFactory.get("FAILED_STEP_EXECUTION_STEP")
				.tasklet((contribution, chunkContext) -> {

					log.debug("***** fail step before");

					// BATCH_STEP_EXECUTION 기록됨, BatchStatus 값을 변경하면, ExitStatus 에 영향을 미친다.
					chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);

					// BATCH_STEP_EXECUTION 기록됨, 실제 다음 STEP 을 못가게 처리하는 코드이며 BatchStatus 값에 영향을 받는다.
					contribution.setExitStatus(ExitStatus.FAILED);

					log.debug("***** fail step after");

					return RepeatStatus.FINISHED;

				})
				.build();
	}
}
