package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.ConfirmPayment;
import dev.thiagooliveira.bankhub.application.usecase.CreatePayableReceivable;
import dev.thiagooliveira.bankhub.domain.dto.ConfirmPaymentInput;
import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.dto.PayableReceivable;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayableReceivableService {

  private final CreatePayableReceivable createPayableReceivable;
  private final ConfirmPayment confirmPayment;

  public PayableReceivableService(
      CreatePayableReceivable createPayableReceivable, ConfirmPayment confirmPayment) {
    this.createPayableReceivable = createPayableReceivable;
    this.confirmPayment = confirmPayment;
  }

  @Transactional
  public List<PayableReceivable> create(CreatePayableReceivableInput input) {
    return this.createPayableReceivable.create(input);
  }

  public PayableReceivable pay(ConfirmPaymentInput input) {
    return this.confirmPayment.pay(input);
  }
}
