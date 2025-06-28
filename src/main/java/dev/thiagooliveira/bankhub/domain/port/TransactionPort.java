package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.model.Transaction;

public interface TransactionPort {

  Transaction create(CreateTransactionEnrichedInput input);

  Page<Transaction> findByAccountIdOrderByDateTimeDesc(GetTransactionPageable param);
}
