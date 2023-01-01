package com.example.skeleton.product.adapter.in.batch.step.processor;

import com.example.skeleton.product.adapter.in.converter.ProductConverter;
import com.example.skeleton.product.adapter.in.model.Product;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@StepScope
@Component(ProductFromItemProcessor.BEAN_NAME)
public class ProductFromItemProcessor implements ItemProcessor<ProductEntity, Product> {

    public static final String BEAN_NAME = "PRODUCT_FROM_ITEM_PROCESSOR";

    @Override
    public Product process(@NonNull final ProductEntity item) {
        return ProductConverter.from(item);
    }
}
