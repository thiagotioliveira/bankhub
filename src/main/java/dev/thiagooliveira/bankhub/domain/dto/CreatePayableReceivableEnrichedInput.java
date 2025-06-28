package dev.thiagooliveira.bankhub.domain.dto;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record CreatePayableReceivableEnrichedInput(
    PayableReceivableType type,
    UUID accountId,
    Category category,
    String description,
    BigDecimal amount,
    LocalDate dueDate,
    boolean recurring,
    Optional<Frequency> frequency,
    Optional<Integer> installmentNumber,
    Optional<Integer> installmentTotal) {
  public CreatePayableReceivableEnrichedInput {
    if (PayableReceivableType.PAYABLE.equals(type) && !CategoryType.DEBIT.equals(category.type()))
      throw new BusinessLogicException("category type needs to be DEBIT");
    else if (PayableReceivableType.RECEIVABLE.equals(type)
        && !CategoryType.CREDIT.equals(category.type()))
      throw new BusinessLogicException("category type needs to be CREDIT");
  }
}
