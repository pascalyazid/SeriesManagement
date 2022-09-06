package com.seriesmanagement.util;

public class SeriesNotFoundException extends RuntimeException {

    public SeriesNotFoundException(String uuid) {super("Could not find Series " + uuid);}
}
