package com.gianfranco.trabajoparcial.service.exception;

public class ClientDeleteException extends RuntimeException {
    public ClientDeleteException() {
        super("Client wasn't deleted. Please try again.");
    }

    public ClientDeleteException(String message) {
        super(message);
    }
}
