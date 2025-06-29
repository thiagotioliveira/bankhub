package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
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

  public Transaction create(CreateTransactionInput input) {
    Category category = null;
    if (input.amount().compareTo(BigDecimal.ZERO) > 0) {
      category =
          getCategory
              .findByType(CategoryType.CREDIT)
              .orElseThrow(() -> BusinessLogicException.notFound("credit category not found"));
    } else if (input.amount().compareTo(BigDecimal.ZERO) < 0) {
      category =
          getCategory
              .findByType(CategoryType.DEBIT)
              .orElseThrow(() -> BusinessLogicException.notFound("debit category not found"));
    } else throw new BusinessLogicException("invalid transaction amount given");

    var account =
        this.getAccount
            .findByIdAndOrganizationId(input.accountId(), input.organizationId())
            .orElseThrow(() -> BusinessLogicException.notFound("account not found"));

    this.accountPort.update(account.id(), account.balance().add(input.amount()));
    return this.transactionPort.create(input.enrichWith(category.id()));
  }
}
