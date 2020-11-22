package org.michalbrzezinski.securitate.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
class Config {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
