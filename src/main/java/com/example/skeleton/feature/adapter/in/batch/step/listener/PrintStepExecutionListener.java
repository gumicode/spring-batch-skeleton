package com.example.skeleton.feature.adapter.in.batch.step.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component(PrintStepExecutionListener.BEAN_NAME)
public class PrintStepExecutionListener implements StepExecutionListener {

	public static final String BEAN_NAME = "PRINT_STEP_EXECUTION_LISTENER";


	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.debug("***** beforeStep ");

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.debug("***** afterStep ");
		return ExitStatus.COMPLETED;
	}
}
