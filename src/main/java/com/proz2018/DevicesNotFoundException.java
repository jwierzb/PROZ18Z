package com.proz2018;


public class DevicesNotFoundException extends RuntimeException {

    public DevicesNotFoundException(String id) {
        super("Could not find employee " + id);
    }
}