package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreatePayableReceivable {

  private final PayableReceivablePort payableReceivablePort;
  private final GetCategory getCategory;
  private final GetAccount getAccount;

  public CreatePayableReceivable(
      PayableReceivablePort payableReceivablePort, GetCategory getCategory, GetAccount getAccount) {
    this.payableReceivablePort = payableReceivablePort;
    this.getCategory = getCategory;
    this.getAccount = getAccount;
  }

  public List<PayableReceivable> create(CreatePayableReceivableInput input) {
    var category =
        this.getCategory
            .findById(input.categoryId(), input.organizationId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    this.getAccount
        .findByIdAndOrganizationId(input.accountId(), input.organizationId())
        .orElseThrow(() -> BusinessLogicException.notFound("account not found"));
    var items = splitIntoMultiple(input.enrichWith(category));
    return items.stream().map(this.payableReceivablePort::create).collect(Collectors.toList());
  }

  private static List<CreatePayableReceivableEnrichedInput> splitIntoMultiple(
      CreatePayableReceivableEnrichedInput input) {
    boolean hasFrequency = input.frequency().isPresent();
    boolean isRecurring = input.recurring();

    if (!hasFrequency || isRecurring) {
      CreatePayableReceivableEnrichedInput item =
          split(input, input.dueDate(), Optional.empty(), input.amount());
      return List.of(item);
    }

    int totalInstallments = input.installmentTotal().orElse(1);
    BigDecimal totalAmount = input.amount();
    BigDecimal[] division = totalAmount.divideAndRemainder(BigDecimal.valueOf(totalInstallments));
    BigDecimal baseAmount = division[0].setScale(2, RoundingMode.DOWN);
    BigDecimal remainder = division[1];

    List<CreatePayableReceivableEnrichedInput> items = new ArrayList<>();
    LocalDate dueDate = input.dueDate();
    Frequency frequency = input.frequency().get();

    for (int i = 1; i <= totalInstallments; i++) {
      BigDecimal amount = baseAmount;
      if (i == totalInstallments) {
        amount = baseAmount.add(remainder);
      }

      items.add(split(input, dueDate, Optional.of(i), amount));

      dueDate =
          switch (frequency) {
            case DAILY -> dueDate.plusDays(1);
            case WEEKLY -> dueDate.plusWeeks(1);
            case MONTHLY -> dueDate.plusMonths(1);
            case YEARLY -> dueDate.plusYears(1);
          };
    }

    return List.copyOf(items);
  }

  private static CreatePayableReceivableEnrichedInput split(
      CreatePayableReceivableEnrichedInput input,
      LocalDate dueDate,
      Optional<Integer> installmentNumber,
      BigDecimal amount) {
    return new CreatePayableReceivableEnrichedInput(
        input.type(),
        input.accountId(),
        input.category(),
        input.description()
            + (input.frequency().isPresent() && input.installmentTotal().isPresent()
                ? " (%d/%d)".formatted(installmentNumber.orElse(1), input.installmentTotal().get())
                : ""),
        amount,
        dueDate,
        input.recurring(),
        input.frequency(),
        installmentNumber,
        input.installmentTotal());
  }
}
