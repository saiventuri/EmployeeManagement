package com.example.employeemanagement.converters;

import org.springframework.core.convert.converter.Converter;

import java.sql.Date;

public class StringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String dateString) {
        return Date.valueOf(dateString);
    }
}
