package com.sda.study.springbootpractice.exceptions;

/**
 * Exception for the school not found
 */
public class SchoolNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public SchoolNotFoundException(Long id) {
        super(String.format("School not found for id: %d", id));
    }

    public SchoolNotFoundException(String name) {
        super(String.format("School not found for id: %s", name));
    }

}
