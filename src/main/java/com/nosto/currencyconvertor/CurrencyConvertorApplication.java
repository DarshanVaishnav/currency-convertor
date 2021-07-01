package com.nosto.currencyconvertor;

import com.nosto.currencyconvertor.service.CurrencyExchangeClient;
import io.github.sercasti.tracing.config.TracingConfig;
import io.github.sercasti.tracing.core.Tracing;
import io.github.sercasti.tracing.filter.TracingFilter;
import io.github.sercasti.tracing.interceptor.TracingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = CurrencyExchangeClient.class)
public class CurrencyConvertorApplication {

  public static void main(String[] args) {
    SpringApplication.run(CurrencyConvertorApplication.class, args);
  }

  @Bean
  protected Tracing tracing() {
    return TracingConfig.createTracing();
  }

  @Bean
  protected TracingFilter tracingFilter() {
    return new TracingFilter();
  }

  @Bean
  protected TracingInterceptor tracingInterceptor() {
    return new TracingInterceptor(tracing());
  }
}
