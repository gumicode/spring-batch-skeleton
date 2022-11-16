package com.example.skeleton.product.adapter.in.batch.step.writer;

import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import com.example.skeleton.shared.application.service.converter.BeanMappedConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class ProductInsertBatchItemWriter {

    public static final String BEAN_NAME = "PRODUCT_INSERT_BATCH_ITEM_WRITER";
    private final DataSource dataSource;

    @Bean(BEAN_NAME)
    @StepScope
    public ItemWriter<ProductEntity> itemWriter() {

        BeanMappedConverter beanMappedConverter = new BeanMappedConverter(ProductEntity.class);
        beanMappedConverter.excludeColumns("id", "createDatetime", "updateDatetime");

        return new JdbcBatchItemWriterBuilder<ProductEntity>()
                .dataSource(dataSource)
                .sql(beanMappedConverter.insertSql())
                .beanMapped()
                .build();
    }
}
