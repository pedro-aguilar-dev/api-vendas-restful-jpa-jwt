package io.pedro.aguilar.config;


import org.apache.tomcat.jni.Local;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration

public class InternacionalizaçãoConfig {


    @Bean
    //o messageSource é a fonte de messagens
    //neste caso a messageSource é a message.properties
    public MessageSource messageSource(){

        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();

        //definir qual arquivo que irá carregar as mensagens
        //neste caso é o messages.properties
        //não é preciso passar a extensão .properties
        messageSource.setBasename("classpath:messages");

        //codifação que reconhece caracteres especias
        messageSource.setDefaultEncoding("ISO-8859-1");


        //caso a aplicação tenha 2 scripts com mensagens de validação em PTBR e Ingles
        //o Locale.getDefault() irá pegar a local do sistema operacional(local onde vc está)
        //e irá automaticamente configurar para o idioma PTBR se for no Brasil
        // e EN em outro pais q fala ingles
        messageSource.setDefaultLocale(Locale.getDefault());

        return messageSource;
    }

    //o LocalValidatorFactoryBean será o objeto responsavel por fazer a interpolação
    //a interpolação é o ato dele passar a mensagem para onde foi definido a chave

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();

        //irá ser passado qual é a fonte de mensagem
        //neste caso está sendo passado o metodo que contém a messageSource definida
        bean.setValidationMessageSource(messageSource());

        return bean;
    }
}
