package com.example.skeleton.shared.adapter.in.batch.incrementer;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

import static java.util.Optional.ofNullable;

public class UniqueRunIdIncrementer extends RunIdIncrementer {
	private static final String RUN_ID = "run.id";

	@Override
	public JobParameters getNext(JobParameters parameters) {
		JobParameters params = ofNullable(parameters).orElse(new JobParameters());
		Long prev = ofNullable(params.getLong(RUN_ID)).orElse(0L);
		return new JobParametersBuilder()
				.addLong(RUN_ID, prev + 1)
				.toJobParameters();
	}
}
