package com.proz2018.exception;


public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException(String id) {
        super("Could not find device " + id);
    }
}