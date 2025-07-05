package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreatePaymentInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import java.time.OffsetDateTime;

public class CreatePayment {

  private final PayableReceivablePort payableReceivablePort; // TODO update to use usecase
  private final GetAccount getAccount;
  private final CreateTransaction createTransaction;

  public CreatePayment(
      PayableReceivablePort payableReceivablePort,
      GetAccount getAccount,
      CreateTransaction createTransaction) {
    this.payableReceivablePort = payableReceivablePort;
    this.getAccount = getAccount;
    this.createTransaction = createTransaction;
  }

  public PayableReceivable create(CreatePaymentInput input) {
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
                target.amount()));

    return this.payableReceivablePort.update(target.markAsPaid(transaction.id()));
  }
}
