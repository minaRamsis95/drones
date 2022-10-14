package com.mina.drones.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class CustomBeans {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(final LocalValidatorFactoryBean factory) {
        return new ValidatingMongoEventListener(factory);
    }
}
