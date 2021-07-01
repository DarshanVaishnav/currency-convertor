package com.nosto.currencyconvertor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nosto.currencyconvertor.enums.Currency;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private Map<Currency, Float> rates;
  private Error error;

  @Data
  static class Error {
    private String code;
    private String message;
  }
}
