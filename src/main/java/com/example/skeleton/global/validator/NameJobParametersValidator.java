package com.example.skeleton.global.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class NameJobParametersValidator implements JobParametersValidator {
	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {

		if (parameters == null) {
			throw new JobParametersInvalidException("job parameter is null");
		}

		if (parameters.getString("name") == null) {
			throw new JobParametersInvalidException("name is null");
		}
	}
}
