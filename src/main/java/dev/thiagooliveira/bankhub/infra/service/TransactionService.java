package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateTransaction;
import dev.thiagooliveira.bankhub.application.usecase.GetTransaction;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final CreateTransaction createTransaction;
  private final GetTransaction getTransaction;

  public TransactionService(CreateTransaction createTransaction, GetTransaction getTransaction) {
    this.createTransaction = createTransaction;
    this.getTransaction = getTransaction;
  }

  public Transaction create(CreateTransactionInput input) {
    return this.createTransaction.create(input);
  }

  public Page<Transaction> findByAccountId(GetTransactionPageable pageable) {
    return this.getTransaction.findByAccountId(pageable);
  }
}
