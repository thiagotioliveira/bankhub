package dev.thiagooliveira.bankhub.domain.dto.projection;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public record TransactionEnriched(
    UUID id,
    UUID accountId,
    OffsetDateTime dateTime,
    String description,
    CategoryEnriched category,
    BigDecimal amount) {
  public TransactionEnriched(
      UUID id,
      UUID accountId,
      Timestamp dateTime,
      String description,
      UUID categoryId,
      String categoryName,
      String categoryType,
      BigDecimal amount) {
    this(
        id,
        accountId,
        dateTime.toInstant().atZone(ZoneId.of("Europe/Lisbon")).toOffsetDateTime(),
        description,
        new CategoryEnriched(categoryId, categoryName, categoryType),
        amount);
  }
}
