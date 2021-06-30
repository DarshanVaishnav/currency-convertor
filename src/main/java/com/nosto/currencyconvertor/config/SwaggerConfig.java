package com.nosto.currencyconvertor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Currency-Convertor")
                .description("Nosto's Currency-Convertor Application")
                .version("v1.0"));
  }
}
