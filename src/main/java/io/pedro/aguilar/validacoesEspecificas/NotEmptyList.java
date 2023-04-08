package io.pedro.aguilar.validacoesEspecificas;


import io.pedro.aguilar.validacoesEspecificas.constraintValidation.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//para a anotação ser verificada em tempo de execução
@Retention(RetentionPolicy.RUNTIME)

//definindo que a anotação irá ser colocada em campos("fields")
@Target(ElementType.FIELD)

//O Constraint define que a notação realmente uma anotação de validação
//no validateBy irá ser passado a classe que vai fazer a validação
@Constraint(validatedBy = NotEmptyListValidator.class)

//a anotação serve apenas para marcar os campos, não irá nenhum metodo abstrato aqui
public @interface NotEmptyList {
    //ao criar uma anotação customizada de validação
    //é preciso que dentro da interface seja definido os 3 metodos abaixo


    //está será a mensagem padrão caso nenhuma seja colocada
    String message () default "A lista não pode ser vazia";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};



}
