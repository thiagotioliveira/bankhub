package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.domain.model.MonthlyAccountSummary;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.YearMonth;

public class CreateAccount {

  private final AccountPort accountPort;
  private final GetBank getBank;
  private final CreateTransaction createTransaction;
  private final GetCategory getCategory;
  private final CreateMonthlyAccountSummary createMonthlyAccountSummary;

  public CreateAccount(
      AccountPort accountPort,
      GetBank getBank,
      CreateTransaction createTransaction,
      GetCategory getCategory,
      CreateMonthlyAccountSummary createMonthlyAccountSummary) {
    this.accountPort = accountPort;
    this.getBank = getBank;
    this.createTransaction = createTransaction;
    this.getCategory = getCategory;
    this.createMonthlyAccountSummary = createMonthlyAccountSummary;
  }

  public Account create(CreateAccountInput input, BigDecimal initialBalance) {
    if (!this.getBank.existsByIdAndOrganizationId(input.bankId(), input.organizationId())) {
      throw BusinessLogicException.notFound("bank not found");
    }
    if (this.accountPort.existsByNameIgnoreCaseAndOrganizationId(
        input.name(), input.organizationId())) {
      throw BusinessLogicException.badRequest("the name already exists");
    }

    var account = this.accountPort.create(input);
    this.createMonthlyAccountSummary.create(
        new MonthlyAccountSummary(
            account.id(),
            YearMonth.now().minusMonths(1),
            account.currency(),
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO));
    if (initialBalance != null && initialBalance.compareTo(BigDecimal.ZERO) != 0) {
      var categoryType = initialBalance.signum() > 0 ? CategoryType.CREDIT : CategoryType.DEBIT;
      var category = this.getCategory.findByType(categoryType).orElseThrow();
      this.createTransaction.create(
          new CreateTransactionInput(
              account.id(),
              account.organizationId(),
              OffsetDateTime.now(),
              "initial balance",
              category.id(),
              initialBalance));
    }
    return accountPort
        .findByIdAndOrganizationId(account.id(), account.organizationId())
        .orElseThrow();
  }
}
