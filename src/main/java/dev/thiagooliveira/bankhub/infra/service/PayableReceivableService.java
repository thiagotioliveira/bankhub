package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.ConfirmPayment;
import dev.thiagooliveira.bankhub.application.usecase.CreatePayableReceivable;
import dev.thiagooliveira.bankhub.domain.dto.ConfirmPaymentInput;
import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.dto.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayableReceivableService {

  private final CreatePayableReceivable createPayableReceivable;
  private final ConfirmPayment confirmPayment;
  private final CategoryService categoryService;

  public PayableReceivableService(
      CreatePayableReceivable createPayableReceivable,
      ConfirmPayment confirmPayment,
      CategoryService categoryService) {
    this.createPayableReceivable = createPayableReceivable;
    this.confirmPayment = confirmPayment;
    this.categoryService = categoryService;
  }

  @Transactional
  public List<PayableReceivable> create(CreatePayableReceivableInput input) {
    var category =
        this.categoryService
            .findById(input.categoryId())
            .orElseThrow(() -> BusinessLogicException.notFound("category not found"));
    return this.createPayableReceivable.create(input.enrichWith(category));
  }

  public PayableReceivable pay(ConfirmPaymentInput input) {
    return this.confirmPayment.pay(input);
  }
}
