package com.example.employeemanagement.errorresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Wrapper class for Response in case of exceptions.
 *
 * @author sai praveen venturi
 */
@AllArgsConstructor
@Getter
public class ErrorMessage {

    /**
     * Status that is to be sent to the rest client
     * in case of exceptions.
     */
    private HttpStatus status;

    /**
     * Information message that is to be sent to Rest client
     * incase of exceptions.
     */
    private String message;
}
