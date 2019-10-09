package com.haud.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import com.haud.utils.Errors;

/**
 * @author Amir
 *
 * This is class is used for handling Custom Exception occurred during Request
 * validation, or any business rule violation.
 */
@Setter
@Getter
public class ClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus httpCode;
    private Errors errors;

    public ClientException(HttpStatus httpCode, Errors errors) {
        this.httpCode = httpCode;
        this.errors = errors;
    }
}
