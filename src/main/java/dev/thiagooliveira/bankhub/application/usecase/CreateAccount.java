package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;

public class CreateAccount {

  private final AccountPort accountPort;

  public CreateAccount(AccountPort accountPort) {
    this.accountPort = accountPort;
  }

  public Account create(CreateAccountInput input) {
    return this.accountPort.create(input);
  }
}
