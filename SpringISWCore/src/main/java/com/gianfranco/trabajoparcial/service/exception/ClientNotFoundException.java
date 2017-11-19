package com.gianfranco.trabajoparcial.service.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super("Client wasn't found. Please try again");
    }

    public ClientNotFoundException(String message) {
        super(message);
    }
}
