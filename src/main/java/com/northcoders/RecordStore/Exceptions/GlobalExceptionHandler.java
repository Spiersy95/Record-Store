package com.northcoders.RecordStore.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmptyDatabaseException.class})
    public ResponseEntity<Object> handleEmptyDatabaseException(EmptyDatabaseException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler
//    public ResponseEntity<Object>  handleInvalidArtistNameException(InvalidArtistNameException e){
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<Object>  handleInvalidIdException(InvalidIdException e){
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<Object>  handleMissingAlbumException(MissingAlbumException e){
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<Object>  handleMissingArtistException(MissingArtistException e){
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<Object>  handleUnavailableYearException(UnavailableYearException e){
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }
}
