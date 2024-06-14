package com.northcoders.RecordStore.Formatters;

public class nameFormatter implements Formatter{

    @Override
    public String format(String str) {
        String formatStr = str.trim();
        formatStr.replace(" ", "_");
        return formatStr;
    }
}
