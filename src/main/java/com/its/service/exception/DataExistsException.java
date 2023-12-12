package com.its.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class DataExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataExistsException() {
    }

    public DataExistsException(String s) {
        super(s);
    }

    public DataExistsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataExistsException(Throwable throwable) {
        super(throwable);
    }

    protected DataExistsException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
