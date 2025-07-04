package dev.thiagooliveira.bankhub.domain.dto.projection;

import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.math.BigDecimal;
import java.util.UUID;

public record AccountEnriched(
    UUID id, String name, BigDecimal balance, Currency currency, BankEnriched bank) {
  public AccountEnriched(
      String id, String name, BigDecimal balance, String currency, String bankId, String bankName) {
    this(
        UUID.fromString(id),
        name,
        balance,
        Currency.valueOf(currency),
        new BankEnriched(UUID.fromString(bankId), bankName));
  }
}
