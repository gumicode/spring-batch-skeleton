package com.example.skeleton.configuration;

import com.example.skeleton.common.CommonBatchParameter;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration extends BasicBatchConfigurer {


	protected BatchConfiguration(BatchProperties properties, DataSource dataSource, TransactionManagerCustomizers transactionManagerCustomizers) {
		super(properties, dataSource, transactionManagerCustomizers);
	}

	@Override
	protected JobLauncher createJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(getJobRepository());
//		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor()); // 비동기로 job 을 응답하고 싶을 때 사용
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(CommonBatchParameter.THREAD_POOL_SIZE);
		taskExecutor.setMaxPoolSize(CommonBatchParameter.THREAD_POOL_SIZE);
		taskExecutor.setThreadNamePrefix("batch-thread-");
		return taskExecutor;
	}
}
