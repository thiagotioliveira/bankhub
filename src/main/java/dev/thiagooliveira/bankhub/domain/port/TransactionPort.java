package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInputExtraInfo;
import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.model.Transaction;

public interface TransactionPort {

  Transaction create(CreateTransactionInput input, CreateTransactionInputExtraInfo extraInfo);

  Page<Transaction> findByAccountIdOrderByDateTimeDesc(GetTransactionPageable param);
}
