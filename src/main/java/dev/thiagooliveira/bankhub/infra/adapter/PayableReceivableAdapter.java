package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.Page;
import dev.thiagooliveira.bankhub.domain.dto.Pageable;
import dev.thiagooliveira.bankhub.domain.dto.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.PayableReceivableEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.PayableReceivableRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PayableReceivableAdapter implements PayableReceivablePort {

  private final PayableReceivableRepository payableReceivableRepository;

  public PayableReceivableAdapter(PayableReceivableRepository payableReceivableRepository) {
    this.payableReceivableRepository = payableReceivableRepository;
  }

  @Override
  @Transactional
  public List<PayableReceivable> create(CreatePayableReceivableEnrichedInput input) {
    List<PayableReceivableEntity> entities = PayableReceivableEntity.from(input);
    List<PayableReceivableEntity> entitiesSaved = new ArrayList<>();
    for (PayableReceivableEntity entity : entities) {
      entitiesSaved.add(this.payableReceivableRepository.save(entity));
    }
    return entitiesSaved.stream()
        .map(PayableReceivableEntity::toReceivableOutput)
        .collect(Collectors.toList());
  }

  @Override
  public Page<PayableReceivable> findByAccountId(Long accountId, Pageable pageable) {
    return null;
  }
}
