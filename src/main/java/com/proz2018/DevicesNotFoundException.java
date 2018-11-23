package com.proz2018;


class DevicesNotFoundException extends RuntimeException {

    DevicesNotFoundException(String id) {
        super("Could not find employee " + id);
    }
}