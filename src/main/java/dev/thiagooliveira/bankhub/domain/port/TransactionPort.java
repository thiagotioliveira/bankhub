package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInputExtraInfo;
import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.model.Transaction;

public interface TransactionPort {

  public Transaction create(
      CreateTransactionInput input, CreateTransactionInputExtraInfo extraInfo);

  public Page<Transaction> findByAccountId(GetTransactionPageable pageable);
}
