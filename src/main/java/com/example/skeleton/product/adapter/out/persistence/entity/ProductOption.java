package com.example.skeleton.product.adapter.out.persistence.entity;

import com.example.skeleton.common.persistence.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "product_option")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}