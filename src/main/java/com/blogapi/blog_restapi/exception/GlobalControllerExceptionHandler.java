package com.blogapi.blog_restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<?> badRequestException(BadRequestException badRequestException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), badRequestException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<?> conflictException(ConflictException conflictException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), conflictException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), resourceNotFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedCharacterException.class)
    protected ResponseEntity<?> unsupportedCharacterException(UnsupportedCharacterException unsupportedCharacterException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), unsupportedCharacterException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    protected ResponseEntity<?> notAuthorizedException(NotAuthorizedException notAuthorizedException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), notAuthorizedException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    protected ResponseEntity<?> nonUniqueResultException(NonUniqueResultException nonUniqueResultException, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), nonUniqueResultException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
}
