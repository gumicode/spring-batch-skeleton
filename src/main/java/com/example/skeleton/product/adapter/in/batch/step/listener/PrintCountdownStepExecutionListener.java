package com.example.skeleton.product.adapter.in.batch.step.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component(PrintCountdownStepExecutionListener.BEAN_NAME)
public class PrintCountdownStepExecutionListener implements StepExecutionListener {

	public static final String BEAN_NAME = "PRINT_COUNTDOWN_STEP_EXECUTION_LISTENER";

	private long startTime;


	@Override
	public void beforeStep(StepExecution stepExecution) {
		this.startTime = System.currentTimeMillis();

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("***** step name : {}, time taken : {}ms", stepExecution.getStepName(), System.currentTimeMillis() - this.startTime);
		return ExitStatus.COMPLETED;
	}
}
