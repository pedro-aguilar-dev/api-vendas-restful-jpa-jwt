package io.pedro.aguilar.exception;

public class PasswordException extends RuntimeException {
    public PasswordException() {
        super("Senhas não batem");
    }
}
