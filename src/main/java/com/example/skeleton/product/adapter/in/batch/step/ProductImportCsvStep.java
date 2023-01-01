package com.example.skeleton.product.adapter.in.batch.step;

import com.example.skeleton.common.CommonBatchParameter;
import com.example.skeleton.product.adapter.in.batch.step.processor.ProductToItemProcessor;
import com.example.skeleton.product.adapter.in.batch.step.reader.ProductFlatFileItemReader;
import com.example.skeleton.product.adapter.in.batch.step.writer.ProductJdbcBatchItemWriter;
import com.example.skeleton.product.adapter.in.model.Product;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductImportCsvStep {

    public static final String BEAN_NAME = "PRODUCT_IMPORT_CSV_STEP";
    private final StepBuilderFactory stepBuilderFactory;
    private final Map<String, ItemReader<Product>> productItemReaders;
    private final Map<String, ItemProcessor<Product, ProductEntity>> productItemProcessor;
    private final Map<String, ItemWriter<ProductEntity>> productItemWriters;

    @Bean(BEAN_NAME)
    public Step step() {
        return stepBuilderFactory.get(BEAN_NAME)
                .<Product, ProductEntity>chunk(CommonBatchParameter.CHUNK)
                .reader(productItemReaders.get(ProductFlatFileItemReader.BEAN_NAME))
                .processor(productItemProcessor.get(ProductToItemProcessor.BEAN_NAME))
                .writer(productItemWriters.get(ProductJdbcBatchItemWriter.BEAN_NAME))
                .build();
    }
}
