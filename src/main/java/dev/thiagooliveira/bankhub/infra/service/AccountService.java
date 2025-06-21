package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateAccount;
import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.model.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private final CreateAccount createAccount;

  public AccountService(CreateAccount createAccount) {
    this.createAccount = createAccount;
  }

  public Account create(CreateAccountInput input) {
    return this.createAccount.create(input);
  }
}
