package dev.thiagooliveira.bankhub.domain.dto;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableTemplate;
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
    LocalDate startDate,
    boolean recurring,
    Optional<Frequency> frequency,
    Optional<Integer> installmentTotal) {

  public CreatePayableReceivableInput {
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

  public static CreatePayableReceivableInput from(CreatePayableReceivableTemplateInput input) {
    return new CreatePayableReceivableInput(
        input.type(),
        input.organizationId(),
        input.accountId(),
        input.categoryId(),
        input.description(),
        input.amount(),
        input.startDate(),
        input.recurring(),
        input.frequency(),
        input.installmentTotal());
  }

  public CreatePayableReceivableTemplateInput toTemplateInput() {
    return new CreatePayableReceivableTemplateInput(
        this.accountId,
        this.organizationId,
        this.categoryId,
        this.description,
        this.amount,
        this.type,
        this.startDate,
        this.recurring,
        this.frequency,
        this.installmentTotal);
  }

  public CreatePayableReceivableEnrichedInput enrichWith(
      PayableReceivableTemplate template, Category category) {
    return new CreatePayableReceivableEnrichedInput(
        this.type,
        this.accountId,
        template,
        category,
        this.description,
        this.amount,
        this.startDate,
        this.recurring,
        this.frequency,
        Optional.empty(),
        this.installmentTotal);
  }
}
