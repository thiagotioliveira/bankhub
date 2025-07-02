package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

public class CreateAccount {

  private final AccountPort accountPort;
  private final GetBank getBank;
  private final CreateTransaction createTransaction;
  private final GetCategory getCategory;
  private final CreateAccountBalanceSnapshot createAccountBalanceSnapshot;

  public CreateAccount(
      AccountPort accountPort,
      GetBank getBank,
      CreateTransaction createTransaction,
      GetCategory getCategory,
      CreateAccountBalanceSnapshot createAccountBalanceSnapshot) {
    this.accountPort = accountPort;
    this.getBank = getBank;
    this.createTransaction = createTransaction;
    this.getCategory = getCategory;
    this.createAccountBalanceSnapshot = createAccountBalanceSnapshot;
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
    var now = LocalDate.now();
    var lastMonth = now.minusMonths(1);
    this.createAccountBalanceSnapshot.create(
        account.id(), lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()), BigDecimal.ZERO);
    if (!Objects.equals(initialBalance, BigDecimal.ZERO)) {
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
