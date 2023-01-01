package com.example.skeleton.common.batch.extractor;

import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvFieldExtractor<T> implements FieldExtractor<T>, InitializingBean {

    private String[] names;
    private static final String QUOTE = "\"";
    private static final String DOUBLE_QUOTE = "\"\"";

    public void setNames(final String[] names) {
        Assert.notNull(names, "Names must be non-null");
        this.names = names.clone();
    }

    @Override
    public Object[] extract(final T item) {
        final List<Object> values = new ArrayList<>();

        final BeanWrapper bw = new BeanWrapperImpl(item);
        for (final String propertyName : this.names) {
            if (Objects.requireNonNull(bw.getPropertyType(propertyName)).isAssignableFrom(String.class)) {
                values.add(doubleQuoteIfString(bw.getPropertyValue(propertyName)));
            } else {
                values.add(bw.getPropertyValue(propertyName));
            }
        }
        return values.toArray();
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(names, "The 'names' property must be set.");
    }

    private Object doubleQuoteIfString(final Object obj) {
        return obj instanceof String ? doubleQuote(handleEmbeddedQuote((String) obj)) : obj;
    }

    private String doubleQuote(final String str) {
        if (StringUtils.hasText(str)) {
            return QUOTE + str + QUOTE;
        }
        return str;
    }

    private String handleEmbeddedQuote(final String str) {
        if (StringUtils.hasText(str) && str.contains(QUOTE)) {
            return str.replace(QUOTE, DOUBLE_QUOTE);
        }
        return str;
    }
}
