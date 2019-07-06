package com.minlia.module.dozer.converter;

import org.dozer.DozerConverter;

import java.time.LocalDate;

/**
 * Created by jiyeon on 15. 9. 6..
 */
public class LocalDateCustomConverter extends DozerConverter<LocalDate, LocalDate> {

    public LocalDateCustomConverter() {
        super(LocalDate.class, LocalDate.class);
    }

    @Override
    public LocalDate convertTo(final LocalDate source, final LocalDate destination) {
        return source;
    }

    @Override
    public LocalDate convertFrom(final LocalDate source, final LocalDate destination) {
        return source;
    }

}