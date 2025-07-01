package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreatePayment;
import dev.thiagooliveira.bankhub.domain.dto.CreatePaymentInput;
import dev.thiagooliveira.bankhub.domain.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private final CreatePayment createPayment;

  public PaymentService(CreatePayment createPayment) {
    this.createPayment = createPayment;
  }

  public Payment create(CreatePaymentInput input) {
    return this.createPayment.create(input);
  }
}
