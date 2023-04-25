package com.example.skeleton.product.adapter.in.batch.step;

import com.example.skeleton.common.CommonBatchParameter;
import com.example.skeleton.product.adapter.in.batch.step.processor.ProductFromItemProcessor;
import com.example.skeleton.product.adapter.in.batch.step.reader.ProductRandomCreateListItemReader;
import com.example.skeleton.product.adapter.in.batch.step.writer.ProductFlatFileItemWriter;
import com.example.skeleton.product.adapter.in.model.Product;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
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
public class ProductExportCsvRandomStep {

    public static final String BEAN_NAME = "PRODUCT_EXPORT_CSV_RANDOM_STEP";
    private final StepBuilderFactory stepBuilderFactory;
    private final Map<String, ItemReader<ProductEntity>> productItemReaders;
    private final Map<String, ItemProcessor<ProductEntity, Product>> productItemProcessor;
    private final Map<String, ItemWriter<Product>> productItemWriters;

    @Bean(BEAN_NAME)
    @JobScope
    public Step step() {
        return stepBuilderFactory.get(BEAN_NAME)
                .<ProductEntity, Product>chunk(CommonBatchParameter.CHUNK)
                .reader(productItemReaders.get(ProductRandomCreateListItemReader.BEAN_NAME))
                .processor(productItemProcessor.get(ProductFromItemProcessor.BEAN_NAME))
                .writer(productItemWriters.get(ProductFlatFileItemWriter.BEAN_NAME))
                .build();
    }
}
