package dev.thiagooliveira.bankhub.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record PayableReceivable(
    UUID id,
    UUID accountId,
    UUID categoryId,
    String description,
    BigDecimal amount,
    LocalDate dueDate,
    PayableReceivableType type,
    PayableReceivableStatus status,
    Optional<Frequency> frequency,
    Optional<Integer> installmentNumber,
    Optional<Integer> installmentTotal,
    Optional<UUID> transactionId) {

  public PayableReceivable markAsPaid(UUID transactionId) {
    return new PayableReceivable(
        this.id,
        this.accountId,
        this.categoryId,
        this.description,
        this.amount,
        this.dueDate,
        this.type,
        PayableReceivableStatus.PAID,
        this.frequency,
        this.installmentNumber,
        this.installmentTotal,
        Optional.of(transactionId));
  }
}
