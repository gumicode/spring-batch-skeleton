package com.example.skeleton.product.adapter.in.batch.step;

import com.example.skeleton.common.CommonBatchParameter;
import com.example.skeleton.feature.adapter.in.batch.step.listener.PrintStepExecutionListener;
import com.example.skeleton.product.adapter.in.batch.listener.PrintCountdownJobExecutionListener;
import com.example.skeleton.product.adapter.in.batch.partitioner.ProductPartitioner;
import com.example.skeleton.product.adapter.in.batch.step.listener.PrintCountdownStepExecutionListener;
import com.example.skeleton.product.adapter.in.batch.step.processor.ProductUpdateItemProcessor;
import com.example.skeleton.product.adapter.in.batch.step.reader.ProductQuerydslNoOffsetItemPartitionReader;
import com.example.skeleton.product.adapter.in.batch.step.writer.ProductJdbcBatchItemWriter;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import java.util.Map;

/**
 * 파티션 STEP 으로 JDBC Batch write 하는 Step
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductUpdateNameJdbcPartitionMultiThreadStep {

    public static final String BEAN_NAME = "PRODUCT_SAVE_RANDOM_JDBC_PARTITION_MULTI_THREAD_STEP";
    private final StepBuilderFactory stepBuilderFactory;
    private final Map<String, Partitioner> partitioner;
    private final Map<String, ItemReader<ProductEntity>> productItemReaders;
    private final Map<String, ItemProcessor<ProductEntity, ProductEntity>> productItemProcessor;
    private final Map<String, ItemWriter<ProductEntity>> productItemWriters;
    private final Map<String, StepExecutionListener> stepListeners;
    private final TaskExecutor taskExecutor;

    @Bean(BEAN_NAME)
    @JobScope
    public Step step() {
        return stepBuilderFactory.get(BEAN_NAME)
                .partitioner(BEAN_NAME, partitioner.get(ProductPartitioner.BEAN_NAME))
                .step(stepBuilderFactory.get(BEAN_NAME)
                        .<ProductEntity, ProductEntity>chunk(CommonBatchParameter.CHUNK)
                        .reader(productItemReaders.get(ProductQuerydslNoOffsetItemPartitionReader.BEAN_NAME))
                        .processor(productItemProcessor.get(ProductUpdateItemProcessor.BEAN_NAME))
                        .writer(productItemWriters.get(ProductJdbcBatchItemWriter.BEAN_NAME))
                        .taskExecutor(taskExecutor)
                        .throttleLimit(CommonBatchParameter.THROTTLE_LIMIT)
                        .build())
                .gridSize(CommonBatchParameter.GRID_SIZE)
                .taskExecutor(taskExecutor)
                .listener(stepListeners.get(PrintCountdownStepExecutionListener.BEAN_NAME))
                .build();
    }
}
