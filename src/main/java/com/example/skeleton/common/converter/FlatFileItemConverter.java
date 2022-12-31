package com.example.skeleton.common.converter;

import com.google.common.base.CaseFormat;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class FlatFileItemConverter {

    public static String[] names(final Field[] fields) {
        return Arrays.stream(fields).map(Field::getName).toArray(String[]::new);
    }

    public static String[] readerNames(Path csvFilePath) {
        try {
            String names =
                    Files.asCharSource(csvFilePath.toFile(), StandardCharsets.UTF_8).readFirstLine();
            if (Objects.isNull(names)) {
                return new String[] {};
            }
            return names.split(",");
        } catch (IOException e) {
            log.error("File path does not exist. : {}", csvFilePath);
            throw new RuntimeException();
        }
    }

    public static String headers(final Field[] fields, final String delimiter) {
        String[] names = names(fields);
        return Arrays.stream(names)
                .map(name -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name))
                .collect(Collectors.joining(delimiter));
    }
}