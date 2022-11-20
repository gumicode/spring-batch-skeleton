package com.example.skeleton.common.converter;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class JdbcBeanMappedConverter {

    private static final String INSERT_PREFIX = "insert into %s(%s) values (%s)";
    private final Class<?> persistence;

    public JdbcBeanMappedConverter(Class<?> persistence) {
        Entity entityAnnotation = persistence.getAnnotation(Entity.class);
        if (entityAnnotation == null) {
            throw new RuntimeException(persistence.getName() + "is not entity");
        }
        this.persistence = persistence;
    }

    public String insertSql(final String... includeColumns) {
        return String.format(INSERT_PREFIX, getTableName(), getColumns(includeColumns), getValues(includeColumns));
    }

    private String getTableName() {
        Table tableAnnotation = persistence.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, persistence.getSimpleName());
        }
        return tableAnnotation.name();
    }

    private String getColumns(final String... includeColumns) {
        return convertColumns(null, includeColumns);
    }


    private String getValues(final String... includeColumns) {
        return convertColumns(":", includeColumns);
    }

    private String convertColumns(final String prefix, final String... includeColumns) {
        return Arrays.stream(includeColumns)
                .map(column -> {
                    String convertColumns = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column);
                    return prefix == null ? convertColumns : prefix + convertColumns;
                }).collect(Collectors.joining(","));
    }

}
