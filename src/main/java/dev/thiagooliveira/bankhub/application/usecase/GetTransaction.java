package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.model.Transaction;
import dev.thiagooliveira.bankhub.domain.port.TransactionPort;

public class GetTransaction {
  private final TransactionPort transactionPort;

  public GetTransaction(TransactionPort transactionPort) {
    this.transactionPort = transactionPort;
  }

  public Page<Transaction> findByAccountId(GetTransactionPageable pageable) {
    return this.transactionPort.findByAccountIdOrderByDateTimeDesc(pageable);
  }
}
