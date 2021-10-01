package org.comppress.comppressbackend.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {

    private final String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
