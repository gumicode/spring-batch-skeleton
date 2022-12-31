package com.example.skeleton.product.adapter.in.batch.step.reader;

import com.example.skeleton.common.converter.FlatFileItemConverter;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.persistence.EntityManagerFactory;
import java.nio.file.Path;

@Configuration
@RequiredArgsConstructor
public class ProductFlatFileItemReader {

    public static final String BEAN_NAME = "PRODUCT_FLAT_FILE_ITEM_READER";
    private final EntityManagerFactory entityManagerFactory;

    @Bean(BEAN_NAME)
    @StepScope
    public FlatFileItemReader<ProductEntity> itemReader() {
        return new FlatFileItemReaderBuilder<ProductEntity>()
                .name(BEAN_NAME)
                .resource(new FileSystemResource("export/product.csv"))
                .delimited()
                .names(FlatFileItemConverter.readerNames("export/product.csv", ",")) // 읽기 위해서 setter 메소드가 필요하다.
                .targetType(ProductEntity.class)
                .linesToSkip(1)
                .build();
    }
}
