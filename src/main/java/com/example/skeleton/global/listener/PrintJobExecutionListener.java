package com.example.skeleton.global.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component(PrintJobExecutionListener.BEAN_NAME)
public class PrintJobExecutionListener implements JobExecutionListener {

	public static final String BEAN_NAME = "PRINT_JOB_EXECUTION_LISTENER";

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.debug("***** beforeJob ");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.debug("***** afterJob ");
	}
}
