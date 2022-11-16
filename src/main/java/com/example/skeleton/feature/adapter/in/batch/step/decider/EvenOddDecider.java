package com.example.skeleton.feature.adapter.in.batch.step.decider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

@Slf4j
@Component(EvenOddDecider.BEAN_NAME)
public class EvenOddDecider implements JobExecutionDecider {

	public static final String BEAN_NAME = "EVEN_ODD_DECIDER";

	private int count = 0;

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

		count++;

		log.debug("count : {}", count);

		if (count % 2 == 0) {
			return new FlowExecutionStatus("EVEN");
		}
		return new FlowExecutionStatus("ODD");
	}
}
