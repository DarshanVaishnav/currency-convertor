package com.nosto.currencyconvertor.service;

import com.nosto.currencyconvertor.enums.Currency;
import java.math.BigDecimal;

/** This interface */
public interface CurrencyConvertorService {

  String getConvertedMonetaryValue(
      Currency sourceCurrency, Currency targetCurrency, BigDecimal value);
}
