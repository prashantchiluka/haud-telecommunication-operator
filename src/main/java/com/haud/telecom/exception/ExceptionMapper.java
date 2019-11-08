package com.haud.telecom.exception;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.haud.telecom.utils.Errors;
import com.haud.telecom.utils.Errors.Error;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionMapper {

    @ExceptionHandler(ClientException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleClientException(ClientException clientException) {

        log.info("ClientException " + clientException.getMessage());

        return clientException.getErrors();
    }
    
    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleMissingServletRequestParameter(ServletRequestBindingException ex) {

        log.info("ServletRequestBindingException" + ex.getMessage());

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Required Parameter/Header Not present").detail(ex.getMessage()).build());
        return errors;
    }
    
    @ExceptionHandler({NumberFormatException.class, MethodArgumentTypeMismatchException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleNumberFormatException(NumberFormatException numberFormatException) {

        log.info("NumberFormatException" + numberFormatException.getMessage());

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Method argument type mismatch").detail("Wrong Input").build());
        return errors;
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {

        log.info("MissingServletRequestParameterException" + ex.getMessage());

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Required Parameter Not present")
                .detail(ex.getParameterName() + " parameter not present").build());
        return errors;
    }
    
    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus
    public Errors handleServiceException(TransactionSystemException ex) {

        log.error("TransactionSystemException " + ex.getMessage(), ex);

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Internal Error").detail("Service is down").build());
        return errors;
    }
    
    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleEntityNotFoundException(JpaObjectRetrievalFailureException ex) {

        log.error("JpaObjectRetrievalFailureException" + ex.getMessage());

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Bad Request").detail("Entity not found").build());
        return errors;
    }
    
    @ExceptionHandler(JDBCConnectionException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Errors handleDBException(JDBCConnectionException connectionException) {

        log.error("JDBCConnectionException " + connectionException.getMessage(), connectionException);

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Internal Error").detail("Service is down").build());
        return errors;
    }
 
    @ExceptionHandler(CannotCreateTransactionException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Errors handleDBException(CannotCreateTransactionException transactionException) {

        log.error("CannotCreateTransactionException " + transactionException.getMessage(), transactionException);

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Internal Error").detail("Service is down").build());
        return errors;
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {

        log.info("HttpRequestMethodNotSupportedException " + ex.getMessage());

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Bad Request").detail(ex.getMessage()).build());
        return errors;
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Errors handleIllegalArgumentException(IllegalArgumentException ex) {

        log.info("IllegalArgumentException" + ex.getMessage());

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Bad Request").detail("Illegal Argument").build());
        return errors;
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Errors handleGenericException(RuntimeException runtimeException) {
        log.error("RuntimeException" + runtimeException.getMessage(), runtimeException);
        Errors errors = new Errors();
        errors.addError(Error.builder().title("Invalid Attempt").detail("Something Went Wrong").build());
        return errors;
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Errors handleGenericException(Exception exception) {

        log.error("Exception" + exception.getMessage(), exception);

        Errors errors = new Errors();
        errors.addError(Error.builder().title("Internal Error").detail("Something Went Wrong").build());
        return errors;
    }
}
