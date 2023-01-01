package com.example.skeleton.product.adapter.in.batch.step.processor;

import com.example.skeleton.product.adapter.in.converter.ProductConverter;
import com.example.skeleton.product.adapter.in.model.Product;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@StepScope
@Component(ProductToItemProcessor.BEAN_NAME)
public class ProductToItemProcessor implements ItemProcessor<Product, ProductEntity> {

    public static final String BEAN_NAME = "PRODUCT_TO_ITEM_PROCESSOR";

    @Override
    public ProductEntity process(@NonNull final Product item) {
        return ProductConverter.to(item);
    }
}
