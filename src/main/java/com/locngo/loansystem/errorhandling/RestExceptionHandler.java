package com.locngo.loansystem.errorhandling;

import com.locngo.loansystem.errorhandling.error.*;
import com.locngo.loansystem.sercurity.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistedException.class)
    public ResponseEntity<Object> handleErrorEmailAlreadyExisted(EmailAlreadyExistedException exception) {
        return this.buildResponseEntity( new ApiError(HttpStatus.CONFLICT, exception));
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<Object> handleInvalidJwtException(InvalidJwtAuthenticationException exception) {
        return this.buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, exception));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException exception) {
        return this.buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, exception));
    }

    @ExceptionHandler(NameAlreadyExistedException.class)
    public ResponseEntity<Object> handleNameAlreadyExisted(NameAlreadyExistedException exception) {
        return this.buildResponseEntity(new ApiError(HttpStatus.CONFLICT, exception));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception) {
        return this.buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, exception));
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<Object> handleInvalidOtpException(InvalidOtpException exception) {
        return this.buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, exception));
    }

    @ExceptionHandler(PhoneNumberAlreadyExistedException.class)
    public ResponseEntity<Object> handlePhoneNumberAlreadyExistedException(PhoneNumberAlreadyExistedException exception) {
        return this.buildResponseEntity(new ApiError(HttpStatus.CONFLICT, exception));
    }

    @ExceptionHandler(IdentityCardAlreadyExistedException.class)
    public ResponseEntity<Object> handleIdentityCardAlreadyExistedException(IdentityCardAlreadyExistedException exception) {
        return this.buildResponseEntity(new ApiError(HttpStatus.CONFLICT, exception));
    }

    @ExceptionHandler(PaymentWasPaidException.class)
    public ResponseEntity<Object> handlePaymentWasPaidException(PaymentWasPaidException exception) {
        return this.buildResponseEntity(new ApiError(HttpStatus.CONFLICT, exception));
    }

    @ExceptionHandler(TransactionWasPaidException.class)
    public ResponseEntity<Object> handleTransactionWasPaidException(TransactionWasPaidException exception) {
        return this.buildResponseEntity(new ApiError(HttpStatus.CONFLICT, exception));
    }

    @ExceptionHandler(NotificationInterruptException.class)
    public ResponseEntity<Object> handleNotificationInterruptException(NotificationInterruptException exception) {
        return this.buildResponseEntity(new ApiError(HttpStatus.FAILED_DEPENDENCY, exception));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError error) {
        return new ResponseEntity(error, error.getHttpStatus());
    }
}
