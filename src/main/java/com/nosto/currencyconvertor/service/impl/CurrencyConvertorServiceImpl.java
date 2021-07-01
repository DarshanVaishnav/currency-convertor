package com.nosto.currencyconvertor.service.impl;

import com.nosto.currencyconvertor.enums.Currency;
import com.nosto.currencyconvertor.model.ExchangeAPIResponse;
import com.nosto.currencyconvertor.service.CachingService;
import com.nosto.currencyconvertor.service.CurrencyConvertorService;
import com.nosto.currencyconvertor.service.CurrencyExchangeClient;
import io.github.sercasti.tracing.Traceable;
import java.util.Locale;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CurrencyConvertorServiceImpl implements CurrencyConvertorService {

  @Autowired private CurrencyExchangeClient currencyExchangeClient;
  @Autowired private CachingService cachingService;

  @Value("${currency.exchange.key}")
  private String accessKey;

  @Override
  @Traceable
  public String getConvertedMonetaryValue(
      Currency sourceCurrency, Currency targetCurrency, Float value) {

    Float conversionRate = getConversionRate(sourceCurrency, targetCurrency);
    Float result = conversionRate * value;

    java.util.Currency currency = java.util.Currency.getInstance(targetCurrency.name());
    java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(Locale.UK);
    format.setCurrency(currency);
    return format.format(result);
  }

  /**
   * Gets the conversion rate either from the cache or from Exchange Rate API
   *
   * @param sourceCurrency
   * @param targetCurrency
   * @return
   */
  @Traceable
  private Float getConversionRate(Currency sourceCurrency, Currency targetCurrency) {

    Float conversionRate =
        cachingService.getConversionRateForCurrency(sourceCurrency, targetCurrency);

    if (conversionRate == null) {
      conversionRate = getConversionRateFromExchange(sourceCurrency, targetCurrency);
    }

    return conversionRate;
  }

  /**
   * Gets the latest currency conversion rates from Exchange Rate API
   *
   * @param sourceCurrency
   * @param targetCurrency
   * @return
   * @throws RuntimeException
   */
  @Traceable
  private Float getConversionRateFromExchange(Currency sourceCurrency, Currency targetCurrency)
      throws RuntimeException {
    try {
      String[] currencies = new String[] {targetCurrency.name(), sourceCurrency.name()};

      ExchangeAPIResponse exchangeAPIResponse =
          currencyExchangeClient.getExchangeRate(
              accessKey, Currency.getBaseCurrency().name(), currencies);

      Map<Currency, Float> rates = exchangeAPIResponse.getRates();
      updateCacheWithLatestConversionRates(sourceCurrency, targetCurrency, rates);

      return rates.get(targetCurrency) / rates.get(sourceCurrency);
    } catch (Exception e) {
      log.error("Error getting exchange rate from currency exchange", e);
      throw new RuntimeException("Error getting exchange rate from currency exchange", e);
    }
  }

  /**
   * Saves the latest rate of conversion from sourceCurrency to targetCurrency and vice versa in
   * Redis
   *
   * @param sourceCurrency
   * @param targetCurrency
   * @param rates
   */
  @Traceable
  private void updateCacheWithLatestConversionRates(
      Currency sourceCurrency, Currency targetCurrency, Map<Currency, Float> rates) {

    cachingService.setConversionRateForCurrency(
        sourceCurrency, targetCurrency, rates.get(sourceCurrency), rates.get(targetCurrency));

    cachingService.setConversionRateForCurrency(
        targetCurrency, sourceCurrency, rates.get(targetCurrency), rates.get(sourceCurrency));
  }
}
