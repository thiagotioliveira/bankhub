package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreatePaymentInput;
import dev.thiagooliveira.bankhub.domain.dto.CreatePaymentWithTransactionInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Payment;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import dev.thiagooliveira.bankhub.domain.port.PaymentPort;
import java.time.OffsetDateTime;

public class CreatePayment {

  private final PayableReceivablePort payableReceivablePort; // TODO update to use usecase
  private final GetAccount getAccount;
  private final CreateTransaction createTransaction;
  private final PaymentPort paymentPort;

  public CreatePayment(
      PayableReceivablePort payableReceivablePort,
      GetAccount getAccount,
      CreateTransaction createTransaction,
      PaymentPort paymentPort) {
    this.payableReceivablePort = payableReceivablePort;
    this.getAccount = getAccount;
    this.createTransaction = createTransaction;
    this.paymentPort = paymentPort;
  }

  public Payment create(CreatePaymentInput input) {
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
            new CreatePaymentWithTransactionInput(
                target.id(), transaction.id(), transaction.dateTime()));
    this.payableReceivablePort.update(target.markAsPaid(payment.id()));
    return payment;
  }
}
