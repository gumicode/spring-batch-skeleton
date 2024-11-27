package com.example.skeleton.product.adapter.in.batch.step.processor;

import com.example.skeleton.product.adapter.in.model.ProductProjection;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@StepScope
@Component(ProductProjectionUpdateItemProcessor.BEAN_NAME)
public class ProductProjectionUpdateItemProcessor implements ItemProcessor<ProductProjection, ProductEntity> {

    public static final String BEAN_NAME = "PRODUCT_PROJECTION_UPDATE_ITEM_PROCESSOR";

    @Override
    public ProductEntity process(@NonNull final ProductProjection item) {
        item.getEntity().setName("UPDATE" + item.getEntity().getName());
        return ProductEntity.builder()
                .id(item.getEntity().getId())
                .name(item.getEntity().getName())
                .image(item.getEntity().getImage())
                .code(item.getEntity().getCode())
                .deleted(item.getEntity().getDeleted())
                .build();
    }
}
