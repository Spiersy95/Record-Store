package com.northcoders.RecordStore.Exceptions;

public class InvalidArtistNameException extends RuntimeException{

    public InvalidArtistNameException(String info){
        super(info);
    }
}
