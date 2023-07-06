package com.example.skeleton.product.adapter.in.batch.step.reader;

import com.example.skeleton.common.CommonBatchParameter;
import com.example.skeleton.common.batch.reader.QuerydslNoOffsetPagingItemReader;
import com.example.skeleton.common.batch.reader.expression.Expression;
import com.example.skeleton.common.batch.reader.options.QuerydslNoOffsetNumberOptions;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static com.example.skeleton.product.adapter.out.persistence.entity.QProductEntity.productEntity;

@Configuration
@RequiredArgsConstructor
public class ProductQuerydslNoOffsetItemPartitionReader {

    public static final String BEAN_NAME = "PRODUCT_QUERYDSL_NO_OFFSET_ITEM_PARTITION_READER";
    private final EntityManagerFactory entityManagerFactory;

    @Bean(BEAN_NAME)
    @StepScope
    public QuerydslNoOffsetPagingItemReader<ProductEntity> itemReader(@Value("#{stepExecutionContext['start']}") Long start,
                                                                      @Value("#{stepExecutionContext['end']}") Long end) {
        return new QuerydslNoOffsetPagingItemReader<>(
                entityManagerFactory,
                CommonBatchParameter.PAGE_SIZE,
                new QuerydslNoOffsetNumberOptions<>(productEntity.id, Expression.ASC, start, end),
                queryFactory -> queryFactory.selectFrom(productEntity));
    }
}
