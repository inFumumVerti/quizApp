package de.dhbw.quizapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
