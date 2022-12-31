package com.example.skeleton.common.converter;

import com.google.common.base.CaseFormat;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class FlatFileItemConverter {

    public static String[] writerNames(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).toArray(String[]::new);
    }

    public static String writerHeaders(final Class<?> clazz, final String delimiter) {
        String[] names = writerNames(clazz);
        return Arrays.stream(names)
                .map(name -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name))
                .collect(Collectors.joining(delimiter));
    }

    public static String[] readerNames(final String path, final String delimiter) {
        try {
            String names =
                    Files.asCharSource(Path.of(path).toFile(), StandardCharsets.UTF_8).readFirstLine();
            if (Objects.isNull(names)) {
                return new String[] {};
            }
            return names.split(delimiter);
        } catch (IOException e) {
            log.error("File path does not exist. : {}", path);
            throw new RuntimeException();
        }
    }
}