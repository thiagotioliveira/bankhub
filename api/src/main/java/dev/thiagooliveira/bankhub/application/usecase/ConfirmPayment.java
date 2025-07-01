package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.ConfirmPaymentInput;
import dev.thiagooliveira.bankhub.domain.dto.CreatePaymentInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import dev.thiagooliveira.bankhub.domain.port.PaymentPort;
import java.time.OffsetDateTime;

public class ConfirmPayment {

  private final PayableReceivablePort payableReceivablePort;
  private final GetAccount getAccount;
  private final CreateTransaction createTransaction;
  private final PaymentPort paymentPort;

  public ConfirmPayment(
      PayableReceivablePort payableReceivablePort,
      GetAccount getAccount,
      CreateTransaction createTransaction,
      PaymentPort paymentPort) {
    this.payableReceivablePort = payableReceivablePort;
    this.getAccount = getAccount;
    this.createTransaction = createTransaction;
    this.paymentPort = paymentPort;
  }

  public PayableReceivable pay(ConfirmPaymentInput input) {
    var target =
        this.payableReceivablePort
            .findByIdAndOrganizationId(input.payableReceivableId(), input.organizationId())
            .orElseThrow(() -> BusinessLogicException.notFound("payable/receivable not found"));
    this.getAccount
        .findByIdAndOrganizationId(target.accountId(), input.organizationId())
        .orElseThrow(() -> BusinessLogicException.notFound("account not found"));

    var transaction =
        this.createTransaction.create(
            new CreateTransactionInput(
                target.accountId(),
                input.organizationId(),
                input.dateTime().orElse(OffsetDateTime.now()),
                input
                    .description()
                    .map(d -> String.format("%s was paid - %s", target.description(), d))
                    .orElse(String.format("%s was paid", target.description())),
                target.categoryId(),
                input.amount().orElse(target.amount())));
    var payment =
        this.paymentPort.create(
            new CreatePaymentInput(target.id(), transaction.id(), transaction.dateTime()));
    return this.payableReceivablePort.update(target.markAsPaid(payment.id()));
  }
}
