package com.example.skeleton.product.adapter.in.batch.step.reader;

import com.example.skeleton.common.converter.FlatFileItemConverter;
import com.example.skeleton.product.adapter.in.batch.module.ProductFieldSetMapper;
import com.example.skeleton.product.adapter.in.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
public class ProductFlatFileItemReader {

    public static final String BEAN_NAME = "PRODUCT_FLAT_FILE_ITEM_READER";

    @Bean(BEAN_NAME)
    @StepScope
    public FlatFileItemReader<Product> itemReader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name(BEAN_NAME)
                .resource(new FileSystemResource("export/product.csv")) // 읽어야 할 리소스 설정
                .delimited().delimiter(",") // 파일의 구분자
                .names(FlatFileItemConverter.readerNames("export/product.csv", ",")) // setter 메소드가 필요하다.
                .comments() // 해당 코멘트 기호가 있는 라인은 무시한다. addComment 로 사용해도 된다..encoding("UTF-8") // 인코딩
                .linesToSkip(1) // 파일상단에 있는 무시할 라인 수
                .saveState(true) // 상태 정보를 저장할 것인지 설정, 멀티스레드 환경에서는 false 지정
                .fieldSetMapper(new ProductFieldSetMapper())
                .build();

        // strict, targetType, fieldSetMapper 는 모두 하나의 세트로 동작한다. fieldSetMapper 를 커스텀으로 구현해놓고 strict 값을 별도로 주면 동작하지않으니 주의
    }
}
