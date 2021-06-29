package com.nosto.currencyconvertor.service;

import com.nosto.currencyconvertor.enums.Currency;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import com.nosto.currencyconvertor.model.ExchangeAPIResponse;
import io.github.sercasti.tracing.Traceable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CurrencyConvertorServiceImpl implements CurrencyConvertorService {

  @Autowired private CurrencyExchangeClient currencyExchangeClient;

  @Value("${currency.exchange.key}")
  private String accessKey;

  /**
   *
   * @param sourceCurrency
   * @param targetCurrency
   * @param value
   * @return
   */
  @Override
  public String getConvertedMonetaryValue(
      Currency sourceCurrency, Currency targetCurrency, BigDecimal value) {
    BigDecimal conversionRate = getConvertedRate(sourceCurrency, targetCurrency);
    BigDecimal result = conversionRate.multiply(value);
    return result.toPlainString();
  }

  /**
   *
   * @param sourceCurrency
   * @param targetCurrency
   * @return
   * @throws RuntimeException
   */
  private BigDecimal getConvertedRate(Currency sourceCurrency, Currency targetCurrency) throws RuntimeException {
    try {
      String[] currencies = new String[] {targetCurrency.name(), sourceCurrency.name()};

      ExchangeAPIResponse exchangeAPIResponse =
          currencyExchangeClient.getExchangeRate(
              accessKey, Currency.getBaseCurrency().name(), currencies);
      Map<Currency, BigDecimal> rates = exchangeAPIResponse.getRates();
      return rates.get(targetCurrency).divide(rates.get(sourceCurrency), 2, RoundingMode.HALF_UP);
    } catch (Exception e) {
      log.error("Error getting exchange rate from currency exchange", e);
      throw new RuntimeException("Error getting exchange rate from currency exchange");
    }
  }
}
