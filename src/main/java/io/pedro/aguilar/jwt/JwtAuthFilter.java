package io.pedro.aguilar.jwt;

import io.pedro.aguilar.service.implementacao.UsuarioServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {



    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioServiceImple usuarioServiceImple;

    public JwtAuthFilter(JwtService jwtService, UsuarioServiceImple usuarioServiceImple) {
        this.jwtService = jwtService;
        this.usuarioServiceImple = usuarioServiceImple;
    }

    //o filtro irá interceptar uma requisição antes de chegar no contexto do spring security
    //dentro da classe será obtido o token e o usuário será carregado pelo UserDetailsSevice
    //antes de processar a requisição, o usuário autenticado será adicionado
    // dentro da sessão, dentro do contexto do spring security
    //o filtro tbm entrará no metodo de configure httpsecurity
    //o filtro irá checar as authorities e permissoões roles na API

    //o usuário será adicionado caso o token seja válido
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
         //o metodo irá obter o token que foi mandado via requisição

        //ao mandar uma requisição, o postman irá mandar junto dela
        // um header chamado authorization
        //o token do usuário  irá ficar no value da header authorization

        //pegando o header authorization
      String authorization =   httpServletRequest.getHeader("Authorization");

      //se ele não for nulo e começar com Bearer, o token estará no padrão JWT
      if(authorization != null  && authorization.startsWith("Bearer"));
      //o token JWT é um Bearer Token

        //Bearer Token é um recurso utilizado para garantir a segurança
        //durante a utilização da API

        //Ele é gerado na autenticação do usuário e será solicitado
        //diversas vezes durante seu uso da API, afim de garantir a autenticidade
        //das requisições e evitar conflitos

        //pegando o token enviado na requisição
        //o token será pego com o palavra Bearer junto
        //no value do authorization é armazenado Bearer token....
        //para pegar somente o token, é usado  o split para separa a string
        //a string será separada por espaço " "
        //o indice 0 será o Bearer
        //abaixo é definido que é começado no 1, ou seja irá começar já no token
        String token = authorization.split("")[1];

        //vendo se o token é válido
        boolean isTokenValid = jwtService.tokenValido(token);

        //se o token for válido
        if(isTokenValid) {
            //se o token for válido,obtenha o username do usuário do token
            String username = jwtService.obterUsername(token);

            //carregando o usuário pelo username
           UserDetails usuario =  usuarioServiceImple.loadUserByUsername(username);

           //colocando o usuário dentro do contexto do spring security

            //é passado 3 parametros no construtor da classe UsernamePasswordAuthenticationToken
            //o primeiro é o o usuario vindo do objeto UserDetails
            //no segundo parametro é passado as credenciais
            //as credenciais são as senhas
            //neste caso não será passado e tbm não é recomendado passar
            //no terceiro parametro é passado as authorities, com as roles do usuário
            UsernamePasswordAuthenticationToken user =
                    new UsernamePasswordAuthenticationToken
                            (usuario, null, usuario.getAuthorities());

            //é feito o que está abaxio para dizer que o objeto user é uma autenticação web
            //é feito isso para ser dito para o contexto do spring security que ela se trata
            //de uma autenticação e uma aplicação web
            user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));


            //contexto do spring security
            //pelo contexto é possivel dizer qual é o usuário autenticado
            //também é possivel pegar o usuário autenticado que está na sessão como o getAuthentication

            //no parametro do metodo setAuthentication é preciso passar um objeto/implmentação
            //do tipo Authentication, por esse motivo é feito a instancia e passado os parametros
            //no construtor da classe UsernamePasswordAuthenticationToken

            //o usuário carregado no pelo UserDetails será jogado para dentro do contexto do
            //spring security
            SecurityContextHolder.getContext().setAuthentication(user);

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
