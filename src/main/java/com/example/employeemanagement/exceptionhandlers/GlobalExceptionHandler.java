package com.example.employeemanagement.exceptionhandlers;

import com.example.employeemanagement.errorresponse.ErrorMessage;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Customize the response for HttpRequestMethodNotSupportedException.
     *
     * @param httpRequestMethodNotSupportedException the exception
     * @param httpHeaders                            the headers to be written to the response
     * @param httpStatus                             the selected response status
     * @param webRequest                             the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleHttpRequestMethodNotSupported(@NotNull HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException, @NotNull HttpHeaders httpHeaders, @NotNull HttpStatus httpStatus, WebRequest webRequest) {
        log.error(httpRequestMethodNotSupportedException.getMessage(), httpRequestMethodNotSupportedException);
        ErrorMessage message = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED, httpRequestMethodNotSupportedException.getMessage(), webRequest.getSessionId());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(message);
    }

    /**
     * Customize the response for HttpMediaTypeNotSupportedException.
     *
     * @param httpMediaTypeNotSupportedException the exception
     * @param httpHeaders                        the headers to be written to the response
     * @param httpStatus                         the selected response status
     * @param webRequest                         the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMediaTypeNotSupported(@NotNull HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException, @NotNull HttpHeaders httpHeaders, @NotNull HttpStatus httpStatus, WebRequest webRequest) {
        log.error(httpMediaTypeNotSupportedException.getMessage(), httpMediaTypeNotSupportedException);
        ErrorMessage message = new ErrorMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, httpMediaTypeNotSupportedException.getMessage(), webRequest.getSessionId());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(message);
    }

    /**
     * Customize the response for MissingPathVariableException.
     *
     * @param missingPathVariableException the exception
     * @param httpHeaders                  the headers to be written to the response
     * @param httpStatus                   the selected response status
     * @param webRequest                   the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleMissingPathVariable(@NotNull MissingPathVariableException missingPathVariableException, @NotNull HttpHeaders httpHeaders, @NotNull HttpStatus httpStatus, WebRequest webRequest) {
        log.error(missingPathVariableException.getMessage(), missingPathVariableException);
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, missingPathVariableException.getMessage(), webRequest.getSessionId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    /**
     * Customize the response for TypeMismatchException.
     *
     * @param typeMismatchException the exception
     * @param httpHeaders           the headers to be written to the response
     * @param httpStatus            the selected response status
     * @param webRequest            the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleTypeMismatch(@NotNull TypeMismatchException typeMismatchException, @NotNull HttpHeaders httpHeaders, @NotNull HttpStatus httpStatus, WebRequest webRequest) {
        log.error(typeMismatchException.getMessage(), typeMismatchException);
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, typeMismatchException.getMessage(), webRequest.getSessionId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    /**
     * A single place to customize the response body of all exception types.
     *
     * @param exception   the exception
     * @param body        the body for the response
     * @param httpHeaders the headers for the response
     * @param httpStatus  the response status
     * @param webRequest  the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleExceptionInternal(@NotNull Exception exception, Object body, @NotNull HttpHeaders httpHeaders, @NotNull HttpStatus httpStatus, WebRequest webRequest) {
        log.error(exception.getMessage(), exception);
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), webRequest.getSessionId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    /**
     * Customize the response for MethodArgumentNotValidException.
     *
     * @param methodArgumentNotValidException the exception
     * @param httpHeaders                     the headers to be written to the response
     * @param httpStatus                      the selected response status
     * @param webRequest                      the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException methodArgumentNotValidException, @NotNull HttpHeaders httpHeaders, @NotNull HttpStatus httpStatus, WebRequest webRequest) {
        log.error(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, methodArgumentNotValidException.getLocalizedMessage(), webRequest.getSessionId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    /**
     * Exception handler to handle employee not found exceptions.
     *
     * @param employeeNotFoundException the exception that is to be handled.
     * @param webRequest                the request received by the controller.
     * @return Response entity containing an error message.
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFoundException, WebRequest webRequest) {
        log.error(employeeNotFoundException.getMessage(), employeeNotFoundException);
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, employeeNotFoundException.getMessage(), webRequest.getSessionId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(message);
    }

    /**
     * Exception handler to handle data access exceptions.
     *
     * @param dataAccessException the exception that is to be handled.
     * @param webRequest          the request received by the controller.
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleDataAccessException(DataAccessException dataAccessException, WebRequest webRequest) {
        log.error(dataAccessException.getMessage(), dataAccessException);
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, dataAccessException.getMessage(), webRequest.getSessionId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}
