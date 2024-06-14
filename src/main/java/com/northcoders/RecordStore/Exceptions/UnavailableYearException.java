package com.northcoders.RecordStore.Exceptions;

public class UnavailableYearException extends RuntimeException{

    public UnavailableYearException(String info){
        super(info);
    }
}
