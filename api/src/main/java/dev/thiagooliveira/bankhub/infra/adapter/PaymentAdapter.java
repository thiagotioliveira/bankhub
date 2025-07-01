package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreatePaymentWithTransactionInput;
import dev.thiagooliveira.bankhub.domain.model.Payment;
import dev.thiagooliveira.bankhub.domain.port.PaymentPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.PaymentEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.PaymentRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PaymentAdapter implements PaymentPort {
  private final PaymentRepository paymentRepository;

  public PaymentAdapter(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Override
  public Payment create(CreatePaymentWithTransactionInput input) {
    return this.paymentRepository.save(PaymentEntity.from(input)).toDomain();
  }

  @Override
  public List<Payment> findByPayableReceivableId(UUID payableReceivableId) {
    return this.paymentRepository.findByPayableReceivableId(payableReceivableId).stream()
        .map(PaymentEntity::toDomain)
        .toList();
  }
}
