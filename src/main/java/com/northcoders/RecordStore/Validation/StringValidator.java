package com.northcoders.RecordStore.Validation;

import org.springframework.stereotype.Component;

@Component
public class StringValidator {


    public boolean validate(String str) {
        return str != null && !str.trim().isEmpty();
    }


}
