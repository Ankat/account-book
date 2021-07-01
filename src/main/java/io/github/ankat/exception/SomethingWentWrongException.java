package io.github.ankat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SomethingWentWrongException extends RuntimeException{
    public SomethingWentWrongException(String message){
        super(message);
    }
}
