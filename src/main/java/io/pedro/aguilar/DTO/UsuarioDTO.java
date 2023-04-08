package io.pedro.aguilar.DTO;

import javax.validation.constraints.NotEmpty;

public class UsuarioDTO {
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String username;
    @NotEmpty(message = "{campo.password.obrigatorio")
    private String password;
    @NotEmpty
    private String[] roles;
}
