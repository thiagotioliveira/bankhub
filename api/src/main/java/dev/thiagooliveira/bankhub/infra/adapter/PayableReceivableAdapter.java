package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.*;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.PayableReceivableEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.PayableReceivableRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PayableReceivableAdapter implements PayableReceivablePort {

  private final PayableReceivableRepository payableReceivableRepository;

  public PayableReceivableAdapter(PayableReceivableRepository payableReceivableRepository) {
    this.payableReceivableRepository = payableReceivableRepository;
  }

  @Override
  public PayableReceivable update(PayableReceivable payableReceivable) {
    return this.payableReceivableRepository
        .save(PayableReceivableEntity.from(payableReceivable))
        .toDomain();
  }

  @Override
  public Optional<PayableReceivable> findByIdAndAccountId(UUID id, UUID accountId) {
    return this.payableReceivableRepository
        .findByIdAndAccountId(id, accountId)
        .map(PayableReceivableEntity::toDomain);
  }

  @Override
  public PayableReceivable create(CreatePayableReceivableEnrichedInput input) {
    PayableReceivableEntity entity = PayableReceivableEntity.from(input);
    return this.payableReceivableRepository.save(entity).toDomain();
  }

  @Override
  public Optional<PayableReceivable> findByIdAndOrganizationId(UUID id, UUID organizationId) {
    return this.payableReceivableRepository
        .findByIdAndOrganizationId(id, organizationId)
        .map(PayableReceivableEntity::toDomain);
  }

  @Override
  public List<PayableReceivable> findByOrganizationId(UUID organizationId) {
    return this.payableReceivableRepository
        .findByOrganizationIdOrderByDueDateAsc(organizationId)
        .stream()
        .map(PayableReceivableEntity::toDomain)
        .toList();
  }
}
