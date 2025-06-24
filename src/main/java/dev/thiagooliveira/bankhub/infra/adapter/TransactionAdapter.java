package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInputExtraInfo;
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
  public Transaction create(
      CreateTransactionInput input, CreateTransactionInputExtraInfo extraInfo) {
    return this.transactionRepository.save(TransactionEntity.from(input, extraInfo)).toDomain();
  }

  @Override
  public Page<Transaction> findByAccountId(GetTransactionPageable pageable) {
    var page =
        this.transactionRepository.findByAccountId(
            pageable.accountId(), PageRequest.of(pageable.pageNumber(), pageable.pageSize()));
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
