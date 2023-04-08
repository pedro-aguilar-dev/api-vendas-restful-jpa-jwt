package io.pedro.aguilar.exception;


import org.springframework.http.HttpStatus;

//esta classe representa uma exception com uma mensagem especifica
public class RegraNegocioException extends RuntimeException{
    public RegraNegocioException(String message) {
        super(message);
    }


}
