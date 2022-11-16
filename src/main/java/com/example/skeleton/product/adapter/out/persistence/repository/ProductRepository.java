package com.example.skeleton.product.adapter.out.persistence.repository;

import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
