package com.nosto.currencyconvertor.model;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nosto.currencyconvertor.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
{
    "success": true,
    "timestamp": 1519296206,
    "base": "USD",
    "date": "2021-03-17",
    "rates": {
        "GBP": 0.72007,
        "JPY": 107.346001,
        "EUR": 0.813399,
    }
}
{"error":{"code":"invalid_access_key","message":"You have not supplied a valid API Access Key. [Technical Support: support@apilayer.com]"}}%
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeAPIResponse {

  private boolean success;
  private long timestamp;
  private String base;
  private String date;
  private Map<Currency, BigDecimal> rates;
  private Error error;

  @Data
  static class Error {
     private String code;
     private String message;
  }

}
