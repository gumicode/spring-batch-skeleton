package com.example.skeleton.product.adapter.out.persistence.entity;

import com.example.skeleton.shared.adapter.out.persistence.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends BaseEntity {
	private String code;
	private String name;
	private String image;
}