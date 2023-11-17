package com.data.server.api.exception;

public class HadoopClientException extends Exception {

    HadoopClientException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public HadoopClientException(final String message) {
        super(message);
    }
}
