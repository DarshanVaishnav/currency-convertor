package com.nosto.currencyconvertor.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@ComponentScan("com.nosto.currencyconvertor")
@Data
public class RedisConfig {

  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private int port;

  @Bean
  LettuceConnectionFactory lettuceConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration =
        new RedisStandaloneConfiguration(this.getHost(), this.getPort());
    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  RedisTemplate<String, Float> redisTemplate() {
    final RedisTemplate<String, Float> template = new RedisTemplate();
    template.setConnectionFactory(lettuceConnectionFactory());
    return template;
  }
}
