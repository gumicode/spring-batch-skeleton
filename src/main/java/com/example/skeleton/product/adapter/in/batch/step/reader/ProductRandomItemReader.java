package com.example.skeleton.product.adapter.in.batch.step.reader;

import com.example.skeleton.common.util.NamingUtil;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import net.bytebuddy.utility.RandomString;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class ProductRandomItemReader {

    public static final String BEAN_NAME = "PRODUCT_RANDOM_ITEM_READER";

    @Bean(BEAN_NAME)
    @StepScope
    public ItemReader<ProductEntity> itemReader(@Value("#{jobParameters[createSize]}") final Integer createSize) {
        return new ListItemReader<>(generateProductEntities(createSize));
    }

    private List<ProductEntity> generateProductEntities(final Integer createSize) {

        ProductEntity[] entities = new ProductEntity[createSize];
        for (int i = 0; i < createSize; i++) {
            String code = RandomString.make();
            String name = NamingUtil.food();
            String image = code + ".png";
            ProductEntity productEntity = ProductEntity.builder()
                    .createDatetime(LocalDateTime.now())
                    .updateDatetime(LocalDateTime.now())
                    .deleted(false)
                    .code(code)
                    .name(name)
                    .image(image)
                    .build();
            entities[i] = productEntity;
        }
        return Stream.of(entities).collect(Collectors.toList());
    }
}
