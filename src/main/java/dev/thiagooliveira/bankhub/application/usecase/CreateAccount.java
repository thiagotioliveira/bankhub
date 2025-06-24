package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class CreateAccount {

  private final AccountPort accountPort;
  private final CreateTransaction createTransaction;

  public CreateAccount(AccountPort accountPort, CreateTransaction createTransaction) {
    this.accountPort = accountPort;
    this.createTransaction = createTransaction;
  }

  public Account create(CreateAccountInput input) {
    var account = this.accountPort.create(input);
    if (!Objects.equals(account.balance(), BigDecimal.ZERO)) {
      this.createTransaction.create(
          new CreateTransactionInput(
              account.id(), OffsetDateTime.now(), "initial balance", account.balance()));
    }
    return account;
  }
}
