package com.example.employeemanagement.converters;

import com.example.employeemanagement.model.Gender;
import org.springframework.core.convert.converter.Converter;


public class StringToGenderConverter implements Converter<String, Gender> {
    @Override
    public Gender convert(String s) {
        try {
            return Gender.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
