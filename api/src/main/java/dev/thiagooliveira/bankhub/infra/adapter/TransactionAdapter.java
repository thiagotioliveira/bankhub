package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.bankhub.domain.model.Transaction;
import dev.thiagooliveira.bankhub.domain.port.TransactionPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.TransactionEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.TransactionRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class TransactionAdapter implements TransactionPort {

  private final TransactionRepository transactionRepository;

  public TransactionAdapter(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Optional<Transaction> findById(UUID id) {
    return this.transactionRepository.findById(id).map(TransactionEntity::toDomain);
  }

  @Override
  public Transaction create(CreateTransactionEnrichedInput input) {
    return this.transactionRepository.save(TransactionEntity.from(input)).toDomain();
  }

  @Override
  public Page<TransactionEnriched> findEnrichedByFiltersOrderByDateTime(
      GetTransactionPageable param) {
    var page =
        this.transactionRepository.findEnrichedByFiltersOrderByDateTime(
            param.accountIds(),
            param.organizationId(),
            PageRequest.of(param.pageable().pageNumber(), param.pageable().pageSize()));
    return new Page<TransactionEnriched>(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages(),
        page.isFirst(),
        page.isLast());
  }

  @Override
  public List<TransactionEnriched> getByAccountIdOrderByDateTime(
      UUID accountId, LocalDate from, LocalDate to) {
    return this.transactionRepository.findByAccountIdOrderByDateTime(accountId, from, to);
  }
}
