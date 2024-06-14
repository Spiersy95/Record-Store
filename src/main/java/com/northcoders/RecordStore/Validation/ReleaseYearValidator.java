package com.northcoders.RecordStore.Validation;

import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class ReleaseYearValidator {

    public boolean validate(int year){
        return year <= Year.now().getValue() && year >= -2000;
    }

}
