package com.example.skeleton.product.adapter.out.persistence.entity;

import com.example.skeleton.common.persistence.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "product")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseEntity {
    private String code;
    private String name;
    private String image;
}