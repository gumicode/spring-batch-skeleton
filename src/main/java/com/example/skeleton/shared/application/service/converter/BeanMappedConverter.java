package com.example.skeleton.shared.application.service.converter;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class BeanMappedConverter {

    private static final String INSERT_PREFIX = "insert into %s(%s) values (%s)";
    private final Class<?> persistence;
    private final Set<String> excludeColumns;

    public BeanMappedConverter(Class<?> persistence) {
        Entity entityAnnotation = persistence.getAnnotation(Entity.class);
        if (entityAnnotation == null) {
            throw new RuntimeException(persistence.getName() + "is not entity");
        }
        this.persistence = persistence;
        this.excludeColumns = new HashSet<>();
    }

    public String insertSql() {
        return String.format(INSERT_PREFIX, getTableName(), getColumns(null, ","), getColumns(":", ", "));
    }

    public void excludeColumns(final String... excludeColumns) {
        this.excludeColumns.addAll(Arrays.asList(excludeColumns));
    }

    private String getTableName() {
        Table tableAnnotation = persistence.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, persistence.getSimpleName());
        }
        return tableAnnotation.name();
    }

    private List<String> getSupperClassFields() {
        return Arrays.stream(persistence.getSuperclass().getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
    }

    private List<String> getFields() {
        return Arrays.stream(persistence.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
    }

    private String getColumns(final String prefix, final String join) {
        List<String> getAllColumns = Stream.concat(getSupperClassFields().stream(), getFields().stream()).collect(Collectors.toList());
        return getAllColumns.stream().filter(column -> !excludeColumns.contains(column))
                .map(column -> {
                    String convertColumns = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column);
                    if (prefix != null) {
                        convertColumns = prefix + convertColumns;
                    }
                    return convertColumns;
                }).collect(Collectors.joining(join));
    }
}
