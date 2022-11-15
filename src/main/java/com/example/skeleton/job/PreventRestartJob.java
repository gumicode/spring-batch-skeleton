package com.example.skeleton.job;

import com.example.skeleton.global.step.PrintExecutionContextStep;
import com.example.skeleton.global.step.PrintJobParameterStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 보통 JOB 은 동일한 파라미터 값으로 들어 올 경우 실패지점 부터 다시 동작한다.
 * preventRestart 옵션을 줄 경우 한번 실패하면 다시는 해당 JOB 을 실행할 수 없도록 처리한다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PreventRestartJob {

	public static final String BEAN_NAME = "PRINT_PREVENT_RESTART_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Map<String, Step> steps;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.preventRestart() // 한번 이상 실패 했던 job 에 대하여 중복 실행 하지 못하도록 한다. ( 기본값은 실패지점부터 다시 시작함 )
				.start(steps.get(PrintJobParameterStep.BEAN_NAME))
				.next(runtimeExceptionStep())
				.next(steps.get(PrintExecutionContextStep.BEAN_NAME))
				.build();
	}

	private Step runtimeExceptionStep() {
		return stepBuilderFactory.get("RUNTIME_EXCEPTION_STEP")
				.tasklet((contribution, chunkContext) -> {

					log.debug("***** 실행");
					throw new RuntimeException();

				})
				.build();
	}
}
