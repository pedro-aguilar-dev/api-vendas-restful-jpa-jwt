package io.pedro.aguilar.controllers;


import io.pedro.aguilar.DTO.CredenciaisDTO;
import io.pedro.aguilar.DTO.TokenDTO;
import io.pedro.aguilar.classesDeDominio.Usuario;
import io.pedro.aguilar.exception.PasswordException;
import io.pedro.aguilar.jwt.JwtService;
import io.pedro.aguilar.service.implementacao.UsuarioServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

     //injetando a instancia da classe UsuarioServiceImple
    @Autowired
    private UsuarioServiceImple usuarioServiceImple;

    //injetando a instancia da classe PasswordEncoder para fazer a criptografia
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario inserirUsuario(@Valid @RequestBody Usuario usuario) {
        //criptografando a senha para ela ir para a base de dados já criptografada
        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());

        //setando a senha criptografada
        usuario.setPassword(senhaCriptografada);

        //retornando o metodo de inserir usuário da classe usuarioServiceImple
        return usuarioServiceImple.inserirUsuario(usuario);
    }

    //metodo para fazer a autenticação do usuário
    @PostMapping("/auth")
    public TokenDTO autenticarUsuario(@RequestBody CredenciaisDTO credenciaisDTO) throws Exception {
        try{
            //o metodo autenticar obriga que seja passado um objeto Usuario no parametro
            //por esse motivo é usado o Usuario.builde para colocar os valores inseridos
            //no objeto credenciaisDTO dentro de um objeto do tipo Usuario

        Usuario usuario =
                Usuario.builder()
                    .username(credenciaisDTO.getUsername())
                    .password(credenciaisDTO.getPassword())
                    .build();
         UserDetails usuarioAutenticado = usuarioServiceImple.autenticar(usuario);

           //gerando um token
        String token = jwtService.gerarToken(usuario);
        //retornando o token
            return new TokenDTO(usuario.getUsername(), token);


        }catch (UsernameNotFoundException ex){
             throw  new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }catch (PasswordException ex2){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex2.getMessage());
        }
    }

}



