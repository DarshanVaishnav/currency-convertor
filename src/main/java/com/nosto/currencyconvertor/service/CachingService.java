package com.nosto.currencyconvertor.service;

import com.nosto.currencyconvertor.enums.Currency;

/** Responsible for all the caching related operations in the currency convertor app */
public interface CachingService {

  Boolean setConversionRateForCurrency(Currency from, Currency to, Float fromValue, Float toValue);

  Float getConversionRateForCurrency(Currency from, Currency to);
}
