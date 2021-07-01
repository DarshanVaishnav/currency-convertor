package com.nosto.currencyconvertor.service;

import com.nosto.currencyconvertor.enums.Currency;

/** This service is responsible for converting the monetary values from one currency to other */
public interface CurrencyConvertorService {

  /**
   * This method converts a given monetary value from one currency to other
   *
   * @param sourceCurrency
   * @param targetCurrency
   * @param value
   * @return
   */
  String getConvertedMonetaryValue(Currency sourceCurrency, Currency targetCurrency, Float value);
}
