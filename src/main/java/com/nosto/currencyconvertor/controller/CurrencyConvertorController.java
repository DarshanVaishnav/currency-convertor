package com.nosto.currencyconvertor.controller;

import com.nosto.currencyconvertor.enums.Currency;
import com.nosto.currencyconvertor.model.ExchangeAPIResponse;
import com.nosto.currencyconvertor.service.CurrencyConvertorServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.nosto.currencyconvertor.service.CurrencyExchangeClient;
import io.github.sercasti.tracing.Traceable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CurrencyConvertorController {

  @Autowired private CurrencyConvertorServiceImpl currencyConvertorService;
  @Autowired private CurrencyExchangeClient currencyExchangeClient;

  @GetMapping("/api/currency-convertor/from/{from}/to/{to}/value/{value}")
  @ResponseBody
  @Traceable
  public String getConvertedMonetaryValue(
      @PathVariable Currency from, @PathVariable Currency to, @PathVariable BigDecimal value) {

    List<String> list = new ArrayList<>();
    list.add(to.name());

//    ExchangeAPIResponse exchangeAPIResponse =
//        currencyExchangeClient.getExchangeRate(
//            "a56d28d06ed40508581888b37d4e7fb2", "EUR", list);
//
//      System.out.println("Response -----" +  exchangeAPIResponse);
//
//
//      System.out.println("DATE -----" +  exchangeAPIResponse.getDate());

    return currencyConvertorService.getConvertedMonetaryValue(from, to, value);
  }
}
