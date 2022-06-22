package by.dvn.scooterrental.configuration;

import by.dvn.scooterrental.handlerexception.ExceptionResponse;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
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
    @ExceptionHandler(HandleBadRequestPath.class)
    public ResponseEntity<ExceptionResponse> notFoundData(HandleBadRequestPath ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

}
