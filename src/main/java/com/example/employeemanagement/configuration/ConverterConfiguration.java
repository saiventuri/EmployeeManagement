package com.example.employeemanagement.configuration;

import com.example.employeemanagement.converters.StringToDateConverter;
import com.example.employeemanagement.converters.StringToGenderConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToGenderConverter());
        registry.addConverter(new StringToDateConverter());
    }
}
