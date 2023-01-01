package com.example.skeleton.product.adapter.in.converter;

import com.example.skeleton.product.adapter.in.model.Product;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;

public class ProductConverter {

    public static Product from(final ProductEntity entity) {
        if(entity == null) {
            return null;
        }

        return Product.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .image(entity.getImage())
                .createDatetime(entity.getCreateDatetime())
                .updateDatetime(entity.getUpdateDatetime())
                .deleted(entity.getDeleted())
                .build();
    }

    public static ProductEntity to(final Product model) {
        if(model == null) {
            return null;
        }

        return ProductEntity.builder()
                .id(model.getId())
                .code(model.getCode())
                .name(model.getName())
                .image(model.getImage())
                .createDatetime(model.getCreateDatetime())
                .updateDatetime(model.getUpdateDatetime())
                .deleted(model.getDeleted())
                .build();
    }
}
