package com.example.skeleton.product.adapter.in.batch.step.writer;

import com.example.skeleton.common.converter.JdbcBeanMappedConverter;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class ProductJdbcBatchItemWriter {

    public static final String BEAN_NAME = "PRODUCT_JDBC_BATCH_ITEM_WRITER";
    private final DataSource dataSource;

    @Bean(BEAN_NAME)
    @StepScope
    public JdbcBatchItemWriter<ProductEntity> itemWriter() {
        return new JdbcBatchItemWriterBuilder<ProductEntity>()
                .dataSource(dataSource)
                .sql(new JdbcBeanMappedConverter(ProductEntity.class).insertSql())
                .beanMapped()
                .build();
    }
}
