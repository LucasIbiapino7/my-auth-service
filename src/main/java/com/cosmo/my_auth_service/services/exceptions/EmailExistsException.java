package com.cosmo.my_auth_service.services.exceptions;

public class EmailExistsException extends RuntimeException{
    public EmailExistsException(String message) {
        super(message);
    }
}
