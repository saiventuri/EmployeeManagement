package com.example.employeemanagement.model;

/**
 * Enumeration for Gender.
 *
 * @author sai praveen venturi
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

    /**
     * The gender type.
     */
    private final String genderType;

    /**
     * Constructor for initializing gender.
     *
     * @param genderType the gender type.
     */
    Gender(final String genderType) {
        this.genderType = genderType;
    }

}
