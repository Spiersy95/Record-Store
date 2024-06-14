package com.northcoders.RecordStore.Exceptions;

public class InvalidGenreException extends RuntimeException{

    public InvalidGenreException(String info){
        super(info);
    }
}
