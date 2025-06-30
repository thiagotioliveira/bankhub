package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.Transaction;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import dev.thiagooliveira.bankhub.domain.port.TransactionPort;

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

  public Transaction create(CreateTransactionInput input) {
    int amountSign = input.amount().signum();

    if (amountSign == 0) {
      throw BusinessLogicException.badRequest("invalid transaction amount: cannot be zero");
    }

    Category category = resolveCategory(input);

    var account =
        getAccount
            .findByIdAndOrganizationId(input.accountId(), input.organizationId())
            .orElseThrow(() -> BusinessLogicException.notFound("account not found"));

    accountPort.update(
        account.id(),
        category.isCredit()
            ? account.balance().add(input.amount())
            : account.balance().subtract(input.amount()));

    return transactionPort.create(input.enrichWith(category.id()));
  }

  private Category resolveCategory(CreateTransactionInput input) {
    return getCategory
        .findById(input.categoryId(), input.organizationId())
        .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
  }
}
