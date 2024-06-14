package com.northcoders.RecordStore.Exceptions;

public class InvalidIdException extends RuntimeException {

    public InvalidIdException(String info){
        super(info);
    }
}
