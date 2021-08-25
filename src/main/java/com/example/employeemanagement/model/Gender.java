package com.example.employeemanagement.model;

/**
 * Enumeration for Gender
 */
public enum Gender {

    /**
     * Male gender.
     */
    MALE("MALE"),

    /**
     * Female gender.
     */
    FEMALE("FEMALE");

    private final String genderType;

    /**
     * Constructor for initializing gender.
     *
     * @param genderType the gender type.
     */
    Gender(String genderType) {
        this.genderType = genderType;
    }
}
