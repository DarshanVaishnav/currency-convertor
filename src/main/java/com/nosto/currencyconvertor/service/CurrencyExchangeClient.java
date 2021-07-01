package com.nosto.currencyconvertor.service;

import com.nosto.currencyconvertor.model.ExchangeAPIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Feign Client to hit Exchange Rate APIs */
@Service
@FeignClient(url = "${currency.exchange.url}", name = "currency-exchange-service")
public interface CurrencyExchangeClient {

  @GetMapping("/v1/latest")
  ExchangeAPIResponse getExchangeRate(
      @RequestParam(name = "access_key") String accessKey,
      @RequestParam(name = "base") String baseCurrency,
      @RequestParam(name = "symbols") String[] currencies);
}
