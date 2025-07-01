package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.bankhub.domain.model.Transaction;
import java.util.Optional;
import java.util.UUID;

public interface TransactionPort {

  Optional<Transaction> findById(UUID id);

  Transaction create(CreateTransactionEnrichedInput input);

  Page<TransactionEnriched> findEnrichedByFiltersOrderByDateTime(GetTransactionPageable param);
}
