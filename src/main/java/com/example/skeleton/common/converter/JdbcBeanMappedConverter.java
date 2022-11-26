package com.example.skeleton.common.converter;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class JdbcBeanMappedConverter {

    private static final String INSERT_QUERY = "insert into %s(%s) values (%s)";
    private static final String UPDATE_QUERY = "update %s set %s where %s";
    private final Class<?> persistence;

    public JdbcBeanMappedConverter(Class<?> persistence) {
        Entity entityAnnotation = persistence.getAnnotation(Entity.class);
        if (entityAnnotation == null) {
            throw new RuntimeException(persistence.getName() + "is not entity");
        }
        this.persistence = persistence;
    }

    public String insertSql() {
        return String.format(INSERT_QUERY, tableName(), insertColumns(columns()), insertValue(values()));
    }

    public String updateSql() {
        return String.format(UPDATE_QUERY, tableName(), updateColumns(columns(), values()), updateWherePrimaryKey());
    }

    private String tableName() {
        Table tableAnnotation = persistence.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, persistence.getSimpleName());
        }
        return tableAnnotation.name();
    }

    private List<String> columns() {
        return entityFields(true);
    }

    private List<String> values() {
        return entityFields(false);
    }

    private List<String> entityFields(final boolean column) {
        List<String> allFields = new ArrayList<>();
        Class<?> targetClass = persistence;
        while (targetClass != null) {
            List<String> fields = Arrays.stream(targetClass.getDeclaredFields())
                    .filter(field -> field.getAnnotation(Id.class) == null)
                    .map(field -> (column) ? fieldToColumn(field) : field.getName())
                    .collect(Collectors.toList());
            allFields.addAll(fields);
            targetClass = targetClass.getSuperclass();
        }
        return allFields;
    }

    private String insertColumns(final List<String> columns) {
        return fieldsPrefix(null, columns);
    }

    private String insertValue(final List<String> values) {
        return fieldsPrefix(":", values);
    }

    private String updateColumns(final List<String> columns, final List<String> values) {
        if (columns.size() != values.size()) {
            log.error("size 가 일치하지 않음. columns size : {}, fields size : {}", columns.size(), values.size());
            throw new RuntimeException();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            stringBuilder.append(columns.get(i));
            stringBuilder.append("=:");
            stringBuilder.append(values.get(i));
            if (i != columns.size() - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

    private String updateWherePrimaryKey() {
        Class<?> targetClass = persistence;
        while (targetClass != null) {
            Optional<Field> fieldOptional = Arrays.stream(targetClass.getDeclaredFields())
                    .filter(field -> field.getAnnotation(Id.class) != null).findFirst();
            if (fieldOptional.isPresent()) {
                String column = fieldToColumn(fieldOptional.get());
                String fields = fieldOptional.get().getName();
                return column + "=:" + fields;
            }
            targetClass = persistence.getSuperclass();
        }
        throw new RuntimeException("id 를 찾을 수 없음");
    }

    private String fieldsPrefix(final String prefix, final List<String> fields) {
        return fields.stream().map(name -> prefix == null ? name : prefix + name).collect(Collectors.joining(","));
    }

    private String fieldToColumn(final Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null && columnAnnotation.name() != null) {
            return columnAnnotation.name();
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
    }

}