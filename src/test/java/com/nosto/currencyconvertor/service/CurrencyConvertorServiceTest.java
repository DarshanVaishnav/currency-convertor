package com.nosto.currencyconvertor.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nosto.currencyconvertor.enums.Currency;
import com.nosto.currencyconvertor.model.ExchangeAPIResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CurrencyConvertorServiceTest {

  @MockBean private CurrencyExchangeClient currencyExchangeClient;

  @MockBean private CachingService cachingService;

  @Autowired private CurrencyConvertorService currencyConvertorService;

  @Test
  public void getConvertedMonetaryValue_getFromCacheTest() {
    Currency from = Currency.USD;
    Currency to = Currency.CAD;
    when(cachingService.getConversionRateForCurrency(from, to)).thenReturn(1.24f);
    String result = currencyConvertorService.getConvertedMonetaryValue(from, to, 10f);
    Assertions.assertEquals("CAD12.40", result);
  }

  @Test
  public void getConvertedMonetaryValue_getFromExchangeApiTest() {
    Currency from = Currency.USD;
    Currency to = Currency.CAD;

    Map<Currency, Float> rates = new HashMap();
    rates.put(Currency.USD, 1.18f);
    rates.put(Currency.CAD, 1.47f);

    ExchangeAPIResponse exchangeAPIResponse =
        ExchangeAPIResponse.builder()
            .success(true)
            .date("2021-07-01")
            .base("EUR")
            .rates(rates)
            .build();

    when(cachingService.getConversionRateForCurrency(from, to)).thenReturn(null);
    when(currencyExchangeClient.getExchangeRate(any(), any(), any()))
        .thenReturn(exchangeAPIResponse);
    String result = currencyConvertorService.getConvertedMonetaryValue(from, to, 10f);
    Assertions.assertEquals("CAD12.46", result);
  }
}
