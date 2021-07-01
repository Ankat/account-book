package io.github.ankat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Account Book", version = "1.0", description = "To keep Account Book clean"))
public class AccountBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountBookApplication.class, args);
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }


}
