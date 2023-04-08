package io.pedro.aguilar.controllers;

import io.pedro.aguilar.errors.ApiErrors;
import io.pedro.aguilar.exception.PedidoStatusException;
import io.pedro.aguilar.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)  //codigo 400
    @ExceptionHandler(RegraNegocioException.class)
    public ApiErrors handleRegraNegocioException(RegraNegocioException msg){
        //capturando a mensagem

       String mensagemCapturada =  msg.getMessage();
        return new ApiErrors(mensagemCapturada);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PedidoStatusException.class)
    public ApiErrors handlePedidoStatusExcpetion(PedidoStatusException msg){
        String mensagemCapturada = msg.getMessage();
        return new ApiErrors(mensagemCapturada);
    }

    //ao enviar o request body e alguma propriedade ser vazia
    //a exception que é retornanda é a MethodArgumentValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodValidException(MethodArgumentNotValidException msg){

        //o getBindingResult é uma "lista" onde é possivel pegar os erros gerados com MethodArgumentValidException
     List<String> errors =   msg.getBindingResult().getAllErrors().stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        //o getDefaultMessage irá retornar a mensagem definida na validação da classe Cliente
        return new ApiErrors(errors);
    }

}
