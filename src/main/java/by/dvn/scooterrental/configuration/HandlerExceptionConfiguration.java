package by.dvn.scooterrental.configuration;

import by.dvn.scooterrental.handlerexception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerExceptionConfiguration {

    @ExceptionHandler(HandleNotFoundExeption.class)
    public ResponseEntity<ExceptionResponse> notFoundData(HandleNotFoundExeption ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HandleBadRequestBody.class)
    public ResponseEntity<ExceptionResponse> badRequestBody(HandleBadRequestBody ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandleBadRequestPath.class)
    public ResponseEntity<ExceptionResponse> badRequestPath(HandleBadRequestPath ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandleBadCondition.class)
    public ResponseEntity<ExceptionResponse> badRequestCondition(HandleBadCondition ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandleNotModified.class)
    public ResponseEntity<ExceptionResponse> notModified(HandleNotModified ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_MODIFIED);
    }

}
