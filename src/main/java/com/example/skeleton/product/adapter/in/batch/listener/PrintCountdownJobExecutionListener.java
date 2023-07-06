package com.example.skeleton.product.adapter.in.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component(PrintCountdownJobExecutionListener.BEAN_NAME)
public class PrintCountdownJobExecutionListener implements JobExecutionListener {

	public static final String BEAN_NAME = "PRINT_COUNTDOWN_JOB_EXECUTION_LISTENER";
	private long startTime;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		this.startTime = System.currentTimeMillis();
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("***** end job, time taken : {}ms", System.currentTimeMillis() - this.startTime);
	}
}
