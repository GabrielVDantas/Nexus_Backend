package br.com.nexus.Nexus.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(String message) {

        super(message);
    }
}
