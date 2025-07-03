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
      UUID id,
      UUID templateId,
      UUID accountId,
      String accountName,
      UUID categoryId,
      String currency,
      String description,
      BigDecimal amount,
      Date dueDate,
      String type,
      String status,
      String frequency,
      Integer installmentNumber,
      Integer installmentTotal,
      UUID paymentId) {
    this(
        id,
        templateId,
        accountId,
        categoryId,
        Currency.valueOf(currency),
        description,
        amount,
        dueDate.toLocalDate(),
        PayableReceivableType.valueOf(type),
        PayableReceivableStatus.valueOf(status),
        Optional.ofNullable(Frequency.valueOf(frequency)),
        Optional.ofNullable(installmentNumber),
        Optional.ofNullable(installmentTotal),
        Optional.ofNullable(paymentId));
  }
}
