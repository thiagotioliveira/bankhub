package dev.thiagooliveira.bankhub.domain.dto.projection;

import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.math.BigDecimal;
import java.util.UUID;

public record AccountEnriched(
    UUID id, String name, BigDecimal balance, Currency currency, BankEnriched bank) {
  public AccountEnriched(
      UUID id, String name, BigDecimal balance, String currency, UUID bankId, String bankName) {
    this(id, name, balance, Currency.valueOf(currency), new BankEnriched(bankId, bankName));
  }
}
