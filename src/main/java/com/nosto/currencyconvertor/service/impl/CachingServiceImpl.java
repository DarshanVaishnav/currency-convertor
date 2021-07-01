package com.nosto.currencyconvertor.service.impl;

import com.nosto.currencyconvertor.enums.Currency;
import com.nosto.currencyconvertor.service.CachingService;
import io.github.sercasti.tracing.Traceable;
import java.nio.ByteBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

@Service
public class CachingServiceImpl implements CachingService {

  @Autowired private LettuceConnectionFactory lettuceConnectionFactory;

  @Value("${redis.ttl}")
  private long timeToLive;

  @Override
  @Traceable
  public Boolean setConversionRateForCurrency(
      Currency from, Currency to, Float fromValue, Float toValue) {
    final RedisConnection connection = lettuceConnectionFactory.getConnection();

    Expiration expiration = Expiration.milliseconds(timeToLive);
    String fromToCurrencyKey = getKeyInFormat(from, to);
    return connection.set(
        fromToCurrencyKey.getBytes(),
        floatToBytes(toValue / fromValue),
        expiration,
        RedisStringCommands.SetOption.UPSERT);
  }

  @Override
  @Traceable
  public Float getConversionRateForCurrency(Currency from, Currency to) {
    Float result = null;
    final RedisConnection connection = lettuceConnectionFactory.getConnection();
    final byte[] requestUsedArray = connection.get(getKeyInFormat(from, to).getBytes());
    if (requestUsedArray != null) {
      result = bytesToFloat(requestUsedArray);
    }
    return result;
  }

  private String getKeyInFormat(Currency from, Currency to) {
    return String.format("%s-%s", from.name(), to.name());
  }

  private byte[] floatToBytes(float value) {
    ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
    buffer.putFloat(value);
    return buffer.array();
  }

  private float bytesToFloat(byte[] bytes) {
    ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
    buffer.put(bytes);
    buffer.flip();
    return buffer.getFloat();
  }
}
