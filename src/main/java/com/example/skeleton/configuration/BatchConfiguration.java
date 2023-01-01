package com.example.skeleton.configuration;

import com.example.skeleton.common.CommonBatchParameter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchConfiguration {

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(CommonBatchParameter.THREAD_POOL_SIZE);
		taskExecutor.setMaxPoolSize(CommonBatchParameter.THREAD_POOL_SIZE);
		taskExecutor.setThreadNamePrefix("batch-thread-");
		return taskExecutor;
	}
}
