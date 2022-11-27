package com.example.skeleton.product.adapter.in.batch.step;

import com.example.skeleton.common.CommonBatchParameter;
import com.example.skeleton.product.adapter.in.batch.step.reader.ProductItemReader;
import com.example.skeleton.product.adapter.in.batch.step.writer.ProductInsertBatchItemWriter;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductCopyStep {

    public static final String BEAN_NAME = "PRODUCT_COPY_STEP";
    private final StepBuilderFactory stepBuilderFactory;
    private final Map<String, ItemReader<ProductEntity>> productItemReaders;
    private final Map<String, ItemWriter<ProductEntity>> productItemWriters;
    private final TaskExecutor taskExecutor;

    @Bean(BEAN_NAME)
    public Step step() {
        return stepBuilderFactory.get(BEAN_NAME)
                .<ProductEntity, ProductEntity>chunk(CommonBatchParameter.CHUNK)
                .reader(productItemReaders.get(ProductItemReader.BEAN_NAME))
                .writer(productItemWriters.get(ProductInsertBatchItemWriter.BEAN_NAME))
                .taskExecutor(taskExecutor)
                .throttleLimit(CommonBatchParameter.THROTTLE_LIMIT)
                .build();
    }
}
