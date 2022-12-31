package com.example.skeleton.product.adapter.in.batch;

import com.example.skeleton.product.adapter.in.batch.step.ProductExportCsvRandomStep;
import com.example.skeleton.product.adapter.in.batch.step.ProductImportCsvStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductImportCsvJob {

    public static final String BEAN_NAME = "PRODUCT_IMPORT_CSV_JOB";
    private final JobBuilderFactory jobBuilderFactory;
    private final Map<String, Step> steps;

    @Bean(BEAN_NAME)
    public Job job() {
        return jobBuilderFactory.get(BEAN_NAME)
                .start(steps.get(ProductExportCsvRandomStep.BEAN_NAME))
                .next(steps.get(ProductImportCsvStep.BEAN_NAME))
                .build();
    }
}
