package com.example.skeleton.product.adapter.in.batch.step.writer;

import com.example.skeleton.common.converter.FlatFileItemConverter;
import com.example.skeleton.product.adapter.out.persistence.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
public class ProductFlatFileItemWriter {

    public static final String BEAN_NAME = "PRODUCT_CSV_FLAT_FILE_ITEM_WRITER";

    @Bean(BEAN_NAME)
    @StepScope
    public FlatFileItemWriter<ProductEntity> itemWriter() {
        return new FlatFileItemWriterBuilder<ProductEntity>()
                .name(BEAN_NAME)
                .resource(new FileSystemResource("export/product.csv"))
                .shouldDeleteIfEmpty(false) // 값이 없다면 삭제할지 정하는 옵션, true : 삭제 / false : 삭제 안함
                .append(false) // 동일한 파일이 있을 경우 row 를 추가할 건지, 삭제후 새로 작성할 건지 정하는 옵션, true : row 추가 / false : 삭제후 다시 작성
                .delimited()
                .delimiter(",") // 컬럼 구분값
                .names(FlatFileItemConverter.writerNames(ProductEntity.class))
                .headerCallback(writer -> writer.write(FlatFileItemConverter.writerHeaders(ProductEntity.class, ",")))
                .build();
    }
}
