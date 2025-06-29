package dev.thiagooliveira.bankhub.domain.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateTransactionInput(
    UUID accountId,
    UUID organizationId,
    OffsetDateTime dateTime,
    String description,
    BigDecimal amount) {

  public CreateTransactionEnrichedInput enrichWith(UUID categoryId) {
    return new CreateTransactionEnrichedInput(
        accountId, organizationId, dateTime, description, amount, categoryId);
  }
}
