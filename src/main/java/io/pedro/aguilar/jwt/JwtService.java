package io.pedro.aguilar.jwt;

import io.jsonwebtoken.*;
import io.pedro.aguilar.VendasApplication;
import io.pedro.aguilar.classesDeDominio.Usuario;
import org.apache.tomcat.jni.Local;
import org.aspectj.weaver.patterns.IToken;
import org.h2.store.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

  //é preciso definir dois atributos para a classe:
    //a expiração do token e a chave de assinatura do token

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    //metodo para gerar o token
    public String gerarToken(Usuario usuario){
        //convertendo a string de expiracao em um objeto long
        Long exString = Long.valueOf(expiracao);

        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(exString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        return Jwts
                .builder()

                //setando o subject
                //no subject é setado o conteúdo/informação que irá ser
                //armazenado no Payload
                //geralmente é armazenado um atributo que identifique o usuário
                //neste caso será o username
                .setSubject(usuario.getUsername())

                //setando a hora e data da expiração
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    //metodo para obter as informações do token
    public Claims obterClaims(String token) throws ExpiredJwtException{

        //para obter as informações do token(claims) é preciso decodifica-lo
        return Jwts
                //o parse irá fazer a decodificação do token
                .parser()

                //para decodifica-lo é preciso da chave de assinatura
                .setSigningKey(chaveAssinatura)

                //passando o token que irá ser decodificado
                .parseClaimsJws(token)
                .getBody();
    }

    //metodo para obter o username do usuario do token
    public String obterUsername(String token){

        //para obter o username é preciso acessar o metodo obterClaims
        //é preciso

        //ele irá obter a informação com o username retirado do Payload
        //o getSubject irá servir para pegar o username
        return(String) obterClaims(token).getSubject();
    }


    //metodo para informar se o token é válido não
    public boolean tokenValido(String token){
        try {

            //instanciando um objeto claims
            //e buscando as informações do token com o metodo obterClaims
            Claims claims = obterClaims(token);

            //pegando a data da expiração do token
            Date dataExpiracao = claims.getExpiration();

            //convertendo a objeto date em um LocalDateTime
            //a conversão é feita para o objeto exibir tanto a hora como a data tbm
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(data);
        }catch (Exception ex){
            return false;
        }
    }



    public static void main(String[] args) {
        //para executar a jwtService em standalone, igual a VendasApplication
        //é preciso definir o  metodo run
        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
        JwtService jwtService = contexto.getBean(JwtService.class);

        //passando o username do usuário do token
        Usuario usuario = Usuario.builder().username("Julia").build();

        //exibindo o token no console
        String token = jwtService.gerarToken(usuario);
        System.out.println(token);

        //exibindo o username
        String username = jwtService.obterUsername(token);
        System.out.println(username);

        //exibindo no console se o token é válido ou não
        boolean isTokenValid = jwtService.tokenValido(token);
        System.out.println("O token é válido? " + isTokenValid);
    }

}
