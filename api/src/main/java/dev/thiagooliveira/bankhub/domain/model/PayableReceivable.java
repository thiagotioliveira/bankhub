package dev.thiagooliveira.bankhub.domain.model;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record PayableReceivable(
    UUID id,
    UUID templateId,
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
    Optional<UUID> paymentId) {

  public PayableReceivable markAsPaid(UUID paymentId) {
    if (this.status == PayableReceivableStatus.PAID) {
      throw BusinessLogicException.badRequest("payable/receivable already paid");
    }
    return new PayableReceivable(
        this.id,
        this.templateId,
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
        Optional.of(paymentId));
  }
}
