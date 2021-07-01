package com.nosto.currencyconvertor.service;

import com.nosto.currencyconvertor.enums.Currency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class CachingServiceTest {

  @Autowired RedisTemplate<String, Float> redisTemplate;

  @Autowired CachingService cachingService;

  @Test
  public void setAndGetConversionRateForCurrency_Test() {

    float fromValue = 1.18f;
    float toValue = 1.47f;

    cachingService.setConversionRateForCurrency(Currency.USD, Currency.CAD, fromValue, toValue);
    float conversionRate = cachingService.getConversionRateForCurrency(Currency.USD, Currency.CAD);

    Assertions.assertEquals(toValue / fromValue, conversionRate);
  }

  @Test
  public void getConversionRateForCurrency_WhenKeyIsMissingTest() {
    Float conversionRate = cachingService.getConversionRateForCurrency(Currency.USD, Currency.EUR);
    Assertions.assertNull(conversionRate);
  }
}
