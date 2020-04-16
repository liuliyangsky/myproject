package com.cherrypicks.myproject.exception;

import com.cherrypicks.myproject.util.ApiCodeStatus;

public class InvalidArgumentException extends BaseException {

    private static final long serialVersionUID = 3437225718264838305L;

    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentException(final String message) {
        super(message);
    }

    public InvalidArgumentException(final Throwable cause) {
        super(cause);
    }

    @Override
    public int getErrorCode() {
        return ApiCodeStatus.ILLEGAL_ARGUMENT;
    }

}
