package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.ConfirmPaymentInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import java.time.OffsetDateTime;

public class ConfirmPayment {

  private final PayableReceivablePort payableReceivablePort;
  private final GetAccount getAccount;
  private final CreateTransaction createTransaction;

  public ConfirmPayment(
      PayableReceivablePort payableReceivablePort,
      GetAccount getAccount,
      CreateTransaction createTransaction) {
    this.payableReceivablePort = payableReceivablePort;
    this.getAccount = getAccount;
    this.createTransaction = createTransaction;
  }

  public PayableReceivable pay(ConfirmPaymentInput input) {
    var target =
        this.payableReceivablePort
            .findByIdAndAccountId(input.payableReceivableId(), input.accountId())
            .orElseThrow(() -> BusinessLogicException.notFound("payable/receivable not found"));
    this.getAccount
        .findByIdAndOrganizationId(target.accountId(), input.organizationId())
        .orElseThrow(() -> BusinessLogicException.notFound("account not found"));

    var transaction =
        this.createTransaction.create(
            new CreateTransactionInput(
                input.accountId(),
                input.organizationId(),
                input.dateTime().orElse(OffsetDateTime.now()),
                input
                    .description()
                    .map(d -> String.format("%s was paid - %s", target.description(), d))
                    .orElse(String.format("%s was paid", target.description())),
                target.categoryId(),
                input.amount().orElse(target.amount())));

    return this.payableReceivablePort.update(target.markAsPaid(transaction.id()));
  }
}
