package com.minlia.module.dozer.converter;

import org.dozer.DozerConverter;

import java.time.LocalDateTime;

/**
 * Created by jiyeon on 15. 9. 6..
 */
public class LocalDateTimeCustomConverter extends DozerConverter<LocalDateTime, LocalDateTime> {

    public LocalDateTimeCustomConverter() {
        super(LocalDateTime.class, LocalDateTime.class);
    }

    @Override
    public LocalDateTime convertTo(final LocalDateTime source, final LocalDateTime destination) {
        if (source == null) {
            return null;
        }
        return source;
    }

    @Override
    public LocalDateTime convertFrom(final LocalDateTime source, final LocalDateTime destination) {
        if (source == null) {
            return null;
        }
        return source;
    }

}