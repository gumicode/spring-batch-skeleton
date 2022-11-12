package com.example.skeleton;

import com.example.skeleton.job.UniqueIncrementerJob;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JobRunner implements ApplicationRunner {

	private final JobLauncher jobLauncher;
	private final JobExplorer jobExplorer;
	private final Map<String, Job> jobs;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Job job = jobs.get(UniqueIncrementerJob.BEAN_NAME);
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder(jobExplorer); // incrementer 동작을 위해 추가
		jobParametersBuilder.addLong("long", 0L);
		jobParametersBuilder.addDouble("double", 0.0);
//		jobParametersBuilder.addDate("date", new Date());
//		jobParametersBuilder.addString("string", UUID.randomUUID().toString());
		JobParameters jobParameters = jobParametersBuilder.getNextJobParameters(job)
				.toJobParameters();

		jobLauncher.run(job, jobParameters);
	}
}
