package dev.thiagooliveira.bankhub.domain.dto;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record CreatePayableReceivableTemplateInput(
    UUID accountId,
    UUID organizationId,
    UUID categoryId,
    String description,
    BigDecimal amount,
    PayableReceivableType type,
    LocalDate startDate,
    boolean recurring,
    Optional<Frequency> frequency,
    Optional<Integer> installmentTotal) {

  public CreatePayableReceivableTemplateInput {
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw new BusinessLogicException("amount must be positive");
    if (LocalDate.now().isAfter(startDate))
      throw new BusinessLogicException("start date cannot be before now");
    if (recurring) {
      if (frequency.isEmpty())
        throw new BusinessLogicException("frequency need to be set when recurring");
      if (installmentTotal.isPresent())
        throw new BusinessLogicException("installment total is not valid when is recurring");
    }
  }

  public CreatePayableReceivableTemplateEnrichedInput enrichWith(Category category) {
    return new CreatePayableReceivableTemplateEnrichedInput(
        this.accountId,
        this.organizationId,
        category,
        this.description,
        this.amount,
        this.type,
        this.startDate,
        this.recurring,
        this.frequency,
        this.installmentTotal);
  }
}
