package com.northcoders.RecordStore.Formatters;

import org.springframework.stereotype.Component;

@Component
public class NameFormatter implements Formatter{

    @Override
    public String format(String str) {
        String formatStr = str.trim();
        return formatStr.replace(" ", "_");
    }
}
