package com.northcoders.RecordStore.Exceptions;

public class MissingArtistException extends RuntimeException {

    public MissingArtistException(String info){
        super(info);
    }
}
