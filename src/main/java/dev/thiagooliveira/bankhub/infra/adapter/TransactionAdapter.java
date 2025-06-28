package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.model.Transaction;
import dev.thiagooliveira.bankhub.domain.port.TransactionPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.TransactionEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class TransactionAdapter implements TransactionPort {

  private final TransactionRepository transactionRepository;

  public TransactionAdapter(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction create(CreateTransactionEnrichedInput input) {
    return this.transactionRepository.save(TransactionEntity.from(input)).toDomain();
  }

  @Override
  public Page<Transaction> findByAccountIdOrderByDateTimeDesc(GetTransactionPageable param) {
    var page =
        this.transactionRepository.findByAccountIdOrderByDateTimeDesc(
            param.accountId(),
            PageRequest.of(param.pageable().pageNumber(), param.pageable().pageSize()));
    return new Page<Transaction>(
        page.getContent().stream().map(TransactionEntity::toDomain).toList(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isFirst(),
        page.isLast());
  }
}
