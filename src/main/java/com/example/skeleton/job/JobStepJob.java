package com.example.skeleton.job;

import com.example.skeleton.global.step.PrintJobParameterStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.core.step.job.JobParametersExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * STEP 에서 JOB 을 실행 시킬 수 있다.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobStepJob {

	public static final String BEAN_NAME = "JOB_STEP_JOB";
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final Map<String, Step> steps;
	private final Map<String, Job> jobs;
	private final JobLauncher jobLauncher;


	@Bean(BEAN_NAME)
	public Job job() {
		return jobBuilderFactory.get(BEAN_NAME)
				.start(steps.get(PrintJobParameterStep.BEAN_NAME))
				.next(jobStep())
				.build();
	}

	private Step jobStep() {
		return stepBuilderFactory.get("JOB_STEP")
				.job(jobs.get(PrintJob.BEAN_NAME))
				.launcher(jobLauncher)
				.parametersExtractor(jobParametersExtractor()) // 이전에 실행했던 Step 에 있는 값을 다음 실행할 Job Step 의 JobParameter 를 사용하고 싶을 때
				.listener(new StepExecutionListener() {
					@Override
					public void beforeStep(StepExecution stepExecution) {
						stepExecution.getExecutionContext().putString("name", "test"); // 이때 JobExecutionContext 가 아니라 StepExecutionContext 에 넣어야 JobStep 에서 데이터를 가져올 수 있다.
					}

					@Override
					public ExitStatus afterStep(StepExecution stepExecution) {
						return null;
					}
				})
				.build();
	}

	private JobParametersExtractor jobParametersExtractor(){
		DefaultJobParametersExtractor defaultJobParametersExtractor = new DefaultJobParametersExtractor();
		defaultJobParametersExtractor.setKeys(new String[]{"name"});
		return defaultJobParametersExtractor;
	}
}
