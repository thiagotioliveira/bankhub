package dev.thiagooliveira.bankhub.domain.dto.projection;

import dev.thiagooliveira.bankhub.domain.model.Currency;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableStatus;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record PayableReceivableEnriched(
    UUID id,
    UUID templateId,
    UUID accountId,
    UUID categoryId,
    Currency currency,
    String description,
    BigDecimal amount,
    LocalDate dueDate,
    PayableReceivableType type,
    PayableReceivableStatus status,
    Optional<Frequency> frequency,
    Optional<Integer> installmentNumber,
    Optional<Integer> installmentTotal,
    Optional<UUID> paymentId) {
  public PayableReceivableEnriched(
      String id,
      String templateId,
      String accountId,
      String accountName,
      String categoryId,
      String currency,
      String description,
      BigDecimal amount,
      Date dueDate,
      String type,
      String status,
      String frequency,
      Integer installmentNumber,
      Integer installmentTotal,
      String paymentId) {
    this(
        UUID.fromString(id),
        UUID.fromString(templateId),
        UUID.fromString(accountId),
        UUID.fromString(categoryId),
        Currency.valueOf(currency),
        description,
        amount,
        dueDate.toLocalDate(),
        PayableReceivableType.valueOf(type),
        PayableReceivableStatus.valueOf(status),
        Optional.ofNullable(frequency != null ? Frequency.valueOf(frequency) : null),
        Optional.ofNullable(installmentNumber),
        Optional.ofNullable(installmentTotal),
        Optional.ofNullable(paymentId == null ? null : UUID.fromString(paymentId)));
  }
}
