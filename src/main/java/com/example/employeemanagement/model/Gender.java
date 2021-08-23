package com.example.employeemanagement.model;

public enum Gender {
    MALE("MALE"), FEMALE("FEMALE");

    private final String genderType;

    Gender(String genderType) {
        this.genderType = genderType;
    }
}
