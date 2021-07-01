package com.nosto.currencyconvertor.controller;

import com.nosto.currencyconvertor.enums.Currency;
import com.nosto.currencyconvertor.service.impl.CurrencyConvertorServiceImpl;
import io.github.sercasti.tracing.Traceable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CurrencyConvertorController {

  @Autowired private CurrencyConvertorServiceImpl currencyConvertorService;

  @GetMapping("/api/currency-convertor/from/{from}/to/{to}/value/{value}")
  @ResponseBody
  @Traceable
  public String getConvertedMonetaryValue(
      @PathVariable Currency from, @PathVariable Currency to, @PathVariable Float value) {
    return currencyConvertorService.getConvertedMonetaryValue(from, to, value);
  }
}
