package dev.thiagooliveira.bankhub.infra.http.mapper;

import dev.thiagooliveira.bankhub.domain.model.Currency;
import org.mapstruct.Mapper;

@Mapper
public interface CurrencyMapper {

  default dev.thiagooliveira.bankhub.spec.http.dto.Currency map(Currency currency) {
    if (currency == null) {
      return null;
    }

    dev.thiagooliveira.bankhub.spec.http.dto.Currency dto =
        new dev.thiagooliveira.bankhub.spec.http.dto.Currency();
    dto.setName(currency.name()); // enum name: EUR, BRL
    dto.setSymbol(currency.symbol()); // symbol: €, R$
    return dto;
  }

  default Currency map(dev.thiagooliveira.bankhub.spec.http.dto.Currency dto) {
    if (dto == null || dto.getName() == null) {
      return null;
    }

    try {
      return Currency.valueOf(dto.getName()); // Assumes name is exactly "EUR" or "BRL"
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid currency name: " + dto.getName(), e);
    }
  }
}
