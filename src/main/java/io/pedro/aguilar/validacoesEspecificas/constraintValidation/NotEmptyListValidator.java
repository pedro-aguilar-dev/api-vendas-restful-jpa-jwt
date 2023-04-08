package io.pedro.aguilar.validacoesEspecificas.constraintValidation;

import io.pedro.aguilar.validacoesEspecificas.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


//A classe ConstraintValidator irá receber 2 parametros
//a anotação criada e o tipo de dado que ele irá validar
public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {

    //a interface obriga a implementar 2 metodos
    // o isValid irá dizer se o objeto list passado no parametro dele é valido ou nao
    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {

        //será valida se a lista não for nula e não estiver vazia
        return list != null && !list.isEmpty();
    }


    //metodo para pegar dados da anotação
    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        constraintAnnotation.message();
    }
}
