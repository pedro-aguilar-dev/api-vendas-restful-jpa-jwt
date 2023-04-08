package io.pedro.aguilar.config;


import io.pedro.aguilar.jwt.JwtAuthFilter;
import io.pedro.aguilar.jwt.JwtService;
import io.pedro.aguilar.service.implementacao.UsuarioServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;


//não há necessdade de colocar a anotacão @Configuration
// a @EnableSecurity já possui a anotação configuration dentro dela


//é preciso herdar a classe WebSecurityConfigureAdapter
//nela será obitdo o metodo para autentificar e fazer as autorizações para acesso na api
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImple usuarioServiceImple;


    @Autowired
    private JwtService jwtService;

    //bean que representa o filter
    @Bean
   public OncePerRequestFilter jwtfilter(){
        return new JwtAuthFilter(jwtService, usuarioServiceImple);
    }

    // a classe PasswordEncoder serve para discriptografar e criptografar senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        //O spring security já possui um pacote com PasswordEncoder


        //o Bcrypt é um algoritmo avançado de autenticação
        //toda vez que o usuário passa uma senha, ele irá faze a criptografia
        //após ele gera um hash, o hash é sempre unico
        //toda vez ao gerar um hash da mesma senha, o hash será diferente
        //isso faz com que a segurança seja muita alta


        //no metodo encode o Bcrypt irá gerar o hash a partir da senha do usuário
        //e ao fazer a discriptografia, ele irá verificar se o hash é compativel
        //com a senha digitada


        //mesmo que dois usuários tenham uma senha igual, o Bcryot consegue
        //a verificação de ambos, pois ele criptografa e gera hashs diferentes
        //mesmo que as senhas sejam iguais

        return new BCryptPasswordEncoder();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //metodo será responsavel por configurar a autentifiação
        //exempo: como o usuário irá verificar a senha
        //é o metodo onde o usuario será autenticado
        //nele tbm é definido de onde irão vir os usuários e senhas  para ele
        //fazer a autenticação

        //o metodo configure Authentication... precisa de um PasswordEncoder
        //é obrigatório passa o Password Encoder


        //testando uma autenticação em memória
        //no parametro do userDetailsService é passado o objeto q foi injetado acima
        //o userDetailsService irá servir para carregar os usuários da base de dados
        auth.userDetailsService(usuarioServiceImple)

                //o passwordEncoder irá fazer a comparação de senhas do usuário
                //com que foi digitado
                //ele faz a criptografia da senha digitada no login com a senha
                //que foi criptografada ao fazer o registro dele na primeira vez
                .passwordEncoder(passwordEncoder());

        //criando um usuário em memória
        // .withUser("Pedro")

        //setando a senha e criptografando ela
        //é preciso obrigatoriamente que a senha seja criptografada
        //o metodo encode da classe Password Encode será resposavel por
        //criptografar a senha
        //.password(passwordEncoder().encode("123"))

        //roles são os perfis de usuário
        //como usuário normal, administrados e etc..
        // .roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //metodo responsavel pelas autorizações
        //por exemplo:  usuário foi autenticado no metodo configure Authentication
        //esse metodo será responsavel por verificar se o usuario possui autoriza
        http
                .csrf().disable()
                //o csfr é uma configuração que permite que haja segurança entre uma
                //aplicação web e o back-end, porém o que está sendo feito nesse projeto
                //não é uma aplicação web e sim uma API RESTFUL


                //metodo que faz as autorizações das requisições
                .authorizeRequests()

                //para acessar a api de clientes ele precisa ser autenticado
                //independente da role, se o usuário for autenticado, ele terá acesso
                .antMatchers("/api/clientes/**").hasRole("USER")
                .antMatchers("/api/produtos/**").hasRole("ADMIN")
                .antMatchers("/api/pedidos/**").hasRole("USER")

                //o permitAll irá fazer com que qualquer usuário com qualquer role consiga
                //fazer a requisição da api de usuários

                //com o permitAll o usuario nao precisa de autenticação para fazer a requisição
                .antMatchers("/api/usuarios/**").permitAll()

                //irá fazer com que para fazer qualquer requisição, o usuário precise
                //estar autenticado
                .anyRequest().authenticated()


                //para acessar a clientes ele precisa ter a role USER
                //roles são perfins de usuário(usuário normal, administrador e etc..
                //.antMatchers("/api/clientes/**").hasRole("USER");


                //authority é como se fosse uma transação
                // .antMatchers("/api/clientes/**").hasAuthority("MANTER USUÁRIO")

                //o spring security permite a utilização da role com uma container de authorities
                //por exemplo: caso o usuário cadastrado tenha a role de administrador
                //o perfil de administrador possui todas as authorities
                // todas as authorities que forem criadas na aplicação podem ser atribuidas
                //ao usuário com a role de administrador


                //também é possivel definir a url como aberta para todos
                //é aberta mesmo que o usuário não esteja autenticado
                //.antMatchers("/api/clientes/**").permitAll()

                //o metodo and faz com metodo volte para sentença raiz, volte para o começo
                //ele volta para objeto http
                .and()


                //o metodo formLogin cria o formulário de login automaticamente
                //tbm é possivel criar um formulário customizado e anexar dentro dele
                //é preciso anexar o caminho dele, caso ele seja um form customizado
                //é preciso colocar o form.html na pasta resources
                //de preferencia criar uma pasta chamada "template"

                //não irá ser mais ser criado sessões de usuários
                //a requisição virou uma estrurtura STATELESS
                //antes com o auhtenticationBasic o usuário já ficava logado na sessão
                //em toda requisição é preciso passar o token
                //se o token não for passado, ele não irá reconhecer o usuário
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()


                //antes de executar o UsernamePasswordAuthenticatoinFilter
                //é preciso que o usuário já tenha sido carregado pelo UserDetailsService
                // e colocado no contexto spring tbm pelo filtro
                .addFilterBefore(jwtfilter(), UsernamePasswordAuthenticationFilter.class);


















        //exemplo
        //é preciso que os campos de name sejam exatamente "username" e "password"
        //<form method="post">
        //<input type="text" name="username"></input>
        //<input type="password" name="password"></input>
        // <button type=submit value="Cadastrar"></button>
        //</for>


    }
}







