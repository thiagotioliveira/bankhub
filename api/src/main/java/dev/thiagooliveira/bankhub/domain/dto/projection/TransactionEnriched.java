package dev.thiagooliveira.bankhub.domain.dto.projection;

import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public record TransactionEnriched(
    UUID id,
    UUID accountId,
    Currency currency,
    OffsetDateTime dateTime,
    String description,
    CategoryEnriched category,
    BigDecimal amount) {
  public TransactionEnriched(
      String id,
      String accountId,
      String currency,
      Timestamp dateTime,
      String description,
      String categoryId,
      String categoryName,
      String categoryType,
      BigDecimal amount) {
    this(
        UUID.fromString(id),
        UUID.fromString(accountId),
        Currency.valueOf(currency),
        dateTime.toInstant().atZone(ZoneId.of("Europe/Lisbon")).toOffsetDateTime(), // TODO
        description,
        new CategoryEnriched(UUID.fromString(categoryId), categoryName, categoryType),
        amount);
  }
}
