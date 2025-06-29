package dev.thiagooliveira.bankhub.domain.dto;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record CreatePayableReceivableInput(
    PayableReceivableType type,
    UUID organizationId,
    UUID accountId,
    UUID categoryId,
    String description,
    BigDecimal amount,
    LocalDate dueDate,
    boolean recurring,
    Optional<Frequency> frequency,
    Optional<Integer> installmentTotal) {
  public CreatePayableReceivableInput {
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw new BusinessLogicException("amount must be positive");
    if (LocalDate.now().isAfter(dueDate))
      throw new BusinessLogicException("due date cannot be before now");
    if (recurring) {
      if (frequency.isEmpty())
        throw new BusinessLogicException("frequency need to be set when recurring");
      if (installmentTotal.isPresent())
        throw new BusinessLogicException("installment total is not valid when is recurring");
    }
  }

  public CreatePayableReceivableEnrichedInput enrichWith(Category category) {
    return new CreatePayableReceivableEnrichedInput(
        this.type,
        this.accountId,
        category,
        this.description,
        this.amount,
        this.dueDate,
        this.recurring,
        this.frequency,
        Optional.empty(),
        this.installmentTotal);
  }
}
