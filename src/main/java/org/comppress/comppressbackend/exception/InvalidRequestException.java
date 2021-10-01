package org.comppress.comppressbackend.exception;

import lombok.Data;

@Data
public class InvalidRequestException extends RuntimeException {

    private final String message;

    public InvalidRequestException(String message) {
        super(message);
        this.message = message;
    }
}
