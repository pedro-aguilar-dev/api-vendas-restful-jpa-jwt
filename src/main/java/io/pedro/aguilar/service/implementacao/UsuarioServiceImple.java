package io.pedro.aguilar.service.implementacao;

import io.pedro.aguilar.classesDeDominio.Usuario;
import io.pedro.aguilar.exception.PasswordException;
import io.pedro.aguilar.repositoriosJPA.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service

//a interface UserDetailsService serve para definir o carregamento do usuário
//vindo da base de dados
public class UsuarioServiceImple implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario inserirUsuario(Usuario usuario){
        return usuarioDAO.save(usuario);
    }


    //metodo que verifica se as senhas batem
    public UserDetails autenticar(Usuario usuario) throws Exception {

        //carregando o usuário da base de dados
        UserDetails user  = loadUserByUsername(usuario.getUsername());

        //1 parametro
        // usuario.getPassoword representa a senha que foi digitada

        //2 parametro
        //o user.getPassword representa a senha que está armazenada no banco

        //a senha digitada irá ser comporada com hash que foi para o banco
        //a senha no banco está criptografada
        //isso foi feito pela classe bcrypt do Password Enconder
        //o matches irá comparar as senhas
       boolean senhasBatem =  passwordEncoder.matches(usuario.getPassword(), user.getPassword());

       if(senhasBatem)
           return user;
       else
           throw new PasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //vendo se o usuário existe na base dados
        Usuario usuario = usuarioDAO.findByUsername(username).orElseThrow( () ->
                new UsernameNotFoundException("Usuário não encontrado na base dados"));

        String[]roles = usuario.isAdmin() ?
                //se o usuario for administrador
        //adicione as seguintes roles ao array string de roles
        new String[]{"USER", "ADMIN"} :
                //se o usuário não for administrador
        //adicione somente o role de usuário padrão
        new String[]{"USER"};

        //retornando a classe User
        //a classe User possui as propriedades que vão ser usadas para setar o username
        //o username irá vir da classe usuario, assim como a senha
        //as roles irão vir do array de string feito acima
            return User
                    .builder()
                    .username(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(roles)
                    .build();

            //outro exemplo com um usuário na memória
        //        return User
//                .builder()
//                .username("Fernando")
//                .password(passwordEncoder.encode("123"))
//                 .roles("USER", "ADMIN")
//                .build();



    }
}
