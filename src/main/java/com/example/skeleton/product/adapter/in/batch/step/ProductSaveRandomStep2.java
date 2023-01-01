package com.example.skeleton.product.adapter.in.batch.step;

import com.example.skeleton.common.CommonBatchParameter;
import com.example.skeleton.product.adapter.in.batch.step.reader.ProductListItemReader;
import com.example.skeleton.product.adapter.in.batch.step.writer.ProductJpaItemWriter;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 랜덤으로 ProductEntity 를 생성하여 Jpa write 하는 Step
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductSaveRandomStep2 {

    public static final String BEAN_NAME = "PRODUCT_SAVE_RANDOM_STEP2";
    private final StepBuilderFactory stepBuilderFactory;
    private final Map<String, ItemReader<ProductEntity>> productItemReaders;
    private final Map<String, ItemWriter<ProductEntity>> productItemWriters;

    @Bean(BEAN_NAME)
    public Step step() {
        return stepBuilderFactory.get(BEAN_NAME)
                .<ProductEntity, ProductEntity>chunk(CommonBatchParameter.CHUNK)
                .reader(productItemReaders.get(ProductListItemReader.BEAN_NAME))
                .writer(productItemWriters.get(ProductJpaItemWriter.BEAN_NAME))
                .build();
    }
}
