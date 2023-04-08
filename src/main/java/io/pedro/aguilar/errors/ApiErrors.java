package io.pedro.aguilar.errors;

import org.springframework.boot.system.ApplicationPid;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public ApiErrors(String mensagemErro){
        this.errors = Arrays.asList(mensagemErro);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}
