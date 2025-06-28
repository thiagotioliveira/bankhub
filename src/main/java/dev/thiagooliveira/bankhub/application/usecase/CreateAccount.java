package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class CreateAccount {

  private final AccountPort accountPort;
  private final GetBank getBank;
  private final CreateTransaction createTransaction;

  public CreateAccount(
      AccountPort accountPort, GetBank getBank, CreateTransaction createTransaction) {
    this.accountPort = accountPort;
    this.getBank = getBank;
    this.createTransaction = createTransaction;
  }

  public Account create(CreateAccountInput input) {
    if (!this.getBank.existsByIdAndOrganizationId(input.bankId(), input.organizationId())) {
      throw BusinessLogicException.notFound("bank not found");
    }
    if (this.accountPort.existsByNameIgnoreCaseAndOrganizationId(
        input.name(), input.organizationId())) {
      throw BusinessLogicException.badRequest("the name already exists");
    }
    var account = this.accountPort.create(input);
    if (!Objects.equals(account.balance(), BigDecimal.ZERO)) {
      this.createTransaction.create(
          new CreateTransactionInput(
              account.id(), OffsetDateTime.now(), "initial balance", account.balance()));
    }
    return account;
  }
}
