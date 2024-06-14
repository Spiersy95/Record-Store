package com.northcoders.RecordStore.Exceptions;

public class EmptyDatabaseException extends RuntimeException{

    public EmptyDatabaseException(String info){
        super(info);
    }
}
