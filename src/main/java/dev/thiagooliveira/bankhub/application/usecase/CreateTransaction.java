package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInputExtraInfo;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.domain.model.Transaction;
import dev.thiagooliveira.bankhub.domain.port.TransactionPort;
import java.math.BigDecimal;

public class CreateTransaction {

  private final TransactionPort transactionPort;
  private final GetCategory getCategory;

  public CreateTransaction(TransactionPort transactionPort, GetCategory getCategory) {
    this.transactionPort = transactionPort;
    this.getCategory = getCategory;
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
    return this.transactionPort.create(input, new CreateTransactionInputExtraInfo(category.id()));
  }
}
