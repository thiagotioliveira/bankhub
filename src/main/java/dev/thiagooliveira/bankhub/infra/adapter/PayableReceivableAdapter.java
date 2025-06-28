package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.*;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.PayableReceivableEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.PayableReceivableRepository;
import org.springframework.stereotype.Component;

@Component
public class PayableReceivableAdapter implements PayableReceivablePort {

  private final PayableReceivableRepository payableReceivableRepository;

  public PayableReceivableAdapter(PayableReceivableRepository payableReceivableRepository) {
    this.payableReceivableRepository = payableReceivableRepository;
  }

  @Override
  public PayableReceivable create(CreatePayableReceivableEnrichedInput input) {
    PayableReceivableEntity entity = PayableReceivableEntity.from(input);
    return this.payableReceivableRepository.save(entity).toReceivableOutput();
  }

  @Override
  public Page<PayableReceivable> findByAccountId(Long accountId, Pageable pageable) {
    return null;
  }

  @Override
  public PayableReceivable maskAsPaid(CreatePaymentInput input) {
    var payableReceivable =
        this.payableReceivableRepository
            .findById(input.payableReceivableId())
            .orElseThrow(() -> new BusinessLogicException("payable/receivable not found"));
    return null;
  }
}
