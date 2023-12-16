package com.enuma.paymentapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfiguration {
    //Creates and configures a LocalValidatorFactoryBean
       @Bean
       public LocalValidatorFactoryBean validator() {
            return new LocalValidatorFactoryBean();
        }
}


