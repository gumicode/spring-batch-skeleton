package com.example.skeleton.product.adapter.in.batch.module;

import com.example.skeleton.product.adapter.in.model.Product;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public class ProductFieldSetMapper implements FieldSetMapper<Product> {
    @Override
    public Product mapFieldSet(@NonNull final FieldSet fieldSet) {
        return Product.builder()
                .id(fieldSet.readLong("id"))
                .code(fieldSet.readString("code"))
                .name(fieldSet.readString("name"))
                .image(fieldSet.readString("image"))
                .createDatetime(LocalDateTime.parse(fieldSet.readString("create_datetime")))
                .updateDatetime(LocalDateTime.parse(fieldSet.readString("update_datetime")))
                .deleted(fieldSet.readBoolean("deleted"))
                .build();
    }
}
