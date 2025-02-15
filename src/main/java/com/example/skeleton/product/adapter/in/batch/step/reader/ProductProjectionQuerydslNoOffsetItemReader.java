package com.example.skeleton.product.adapter.in.batch.step.reader;

import com.example.skeleton.common.CommonBatchParameter;
import com.example.skeleton.common.batch.reader.QuerydslNoOffsetPagingItemReader;
import com.example.skeleton.common.batch.reader.expression.Expression;
import com.example.skeleton.common.batch.reader.options.QuerydslNoOffsetNumberOptions;
import com.example.skeleton.product.adapter.in.model.ProductProjection;
import com.example.skeleton.product.adapter.in.model.QProductProjection;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static com.example.skeleton.product.adapter.out.persistence.entity.QProductEntity.productEntity;

@Configuration
@RequiredArgsConstructor
public class ProductProjectionQuerydslNoOffsetItemReader {

    public static final String BEAN_NAME = "PRODUCT_PROJECTION_QUERYDSL_NO_OFFSET_ITEM_READER";
    private final EntityManagerFactory entityManagerFactory;

    @Bean(BEAN_NAME)
    @StepScope
    public QuerydslNoOffsetPagingItemReader<ProductProjection> itemReader() {

        QProductProjection projection = new QProductProjection(productEntity.id, productEntity);

        return new QuerydslNoOffsetPagingItemReader<>(
                entityManagerFactory,
                CommonBatchParameter.PAGE_SIZE,
                new QuerydslNoOffsetNumberOptions<>(Expressions.numberPath(Long.class, "productEntity.id"), Expression.ASC),
                queryFactory -> queryFactory.select(projection).from(productEntity));
    }
}
