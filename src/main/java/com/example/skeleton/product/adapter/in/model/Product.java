package com.example.skeleton.product.adapter.in.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    private Long id;
    private String code;
    private String name;
    private String image;
    private LocalDateTime createDatetime;
    private LocalDateTime updateDatetime;
    private Boolean deleted;
}
