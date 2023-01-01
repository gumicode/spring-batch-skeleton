package com.example.skeleton.product.adapter.in.batch.step.writer;

import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class ProductJpaItemWriter {

    public static final String BEAN_NAME = "PRODUCT_JPA_ITEM_WRITER";
    private final EntityManagerFactory entityManagerFactory;

    @Bean(BEAN_NAME)
    @StepScope
    @Transactional
    public JpaItemWriter<ProductEntity> itemWriter() {
        return new JpaItemWriterBuilder<ProductEntity>()
                .usePersist(true) // 기본값 true
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
