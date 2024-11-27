package com.example.skeleton.product.adapter.in.model;

import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ProductProjection implements Serializable {

    private Long id;
    private ProductEntity entity;

    @QueryProjection
    public ProductProjection(final Long id, final ProductEntity entity) {
        if (entity == null) {
            return;
        }
        this.id = id;
        this.entity = entity;
    }
}
