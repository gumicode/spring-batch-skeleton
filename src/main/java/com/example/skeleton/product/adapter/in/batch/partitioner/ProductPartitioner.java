package com.example.skeleton.product.adapter.in.batch.partitioner;

import com.example.skeleton.common.batch.partition.QuerydslNoOffsetPartitioner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static com.example.skeleton.product.adapter.out.persistence.entity.QProductEntity.productEntity;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductPartitioner {

    public static final String BEAN_NAME = "PRODUCT_PARTITIONER";
    private final EntityManagerFactory entityManagerFactory;

    @Bean(BEAN_NAME)
    public Partitioner partitioner() {
        return new QuerydslNoOffsetPartitioner<>(
                entityManagerFactory,
                jpaQueryFactory ->
                        jpaQueryFactory
                                .select(productEntity.id.min())
                                .from(productEntity)
                                .orderBy(productEntity.id.asc())
                                .limit(1),
                jpaQueryFactory ->
                        jpaQueryFactory
                                .select(productEntity.id.max())
                                .from(productEntity)
                                .orderBy(productEntity.id.desc())
                                .limit(1));
    }
}
