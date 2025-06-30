package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.Transaction;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import dev.thiagooliveira.bankhub.domain.port.TransactionPort;

import java.math.BigDecimal;

public class CreateTransaction {

  private final TransactionPort transactionPort;
  private final AccountPort accountPort;
  private final GetCategory getCategory;
  private final GetAccount getAccount;

  public CreateTransaction(
      TransactionPort transactionPort,
      AccountPort accountPort,
      GetCategory getCategory,
      GetAccount getAccount) {
    this.transactionPort = transactionPort;
    this.accountPort = accountPort;
    this.getCategory = getCategory;
    this.getAccount = getAccount;
  }

  public Transaction createDeposit(CreateTransactionInput input) {
    verifyAmount(input);
    Category category = resolveCategory(input);
    if (!category.isCredit()) {
      throw BusinessLogicException.badRequest("category needs to be credit");
    }
    Account account = resolveAccount(input);

    updateAccount(account, category, input.amount());
    return transactionPort.create(input.enrichWith(category.id()));
  }

  public Transaction createWithdrawal(CreateTransactionInput input) {
    verifyAmount(input);
    Category category = resolveCategory(input);
    if (!category.isDebit()) {
      throw BusinessLogicException.badRequest("category needs to be debit");
    }
    Account account = resolveAccount(input);

    updateAccount(account, category, input.amount());
    return transactionPort.create(input.enrichWith(category.id()));
  }

  public Transaction create(CreateTransactionInput input) {
    verifyAmount(input);
    Category category = resolveCategory(input);

    Account account = resolveAccount(input);

    updateAccount(account, category, input.amount());
    return transactionPort.create(input.enrichWith(category.id()));
  }

  private void updateAccount(Account account, Category category, BigDecimal amount) {
    accountPort.update(
            account.id(),
            category.isCredit()
                    ? account.balance().add(amount)
                    : account.balance().subtract(amount));
  }

  private Account resolveAccount(CreateTransactionInput input) {
    return getAccount
        .findByIdAndOrganizationId(input.accountId(), input.organizationId())
        .orElseThrow(() -> BusinessLogicException.notFound("account not found"));
  }

  private static void verifyAmount(CreateTransactionInput input) {
    int amountSign = input.amount().signum();

    if (amountSign == 0) {
      throw BusinessLogicException.badRequest("invalid transaction amount: cannot be zero");
    }
  }

  private Category resolveCategory(CreateTransactionInput input) {
    return getCategory
        .findById(input.categoryId(), input.organizationId())
        .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
  }
}
