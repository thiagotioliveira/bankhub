package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateAccount;
import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.model.Account;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

  private final CreateAccount createAccount;

  public AccountService(CreateAccount createAccount) {
    this.createAccount = createAccount;
  }

  @Transactional
  public Account create(CreateAccountInput input, BigDecimal initialBalance) {
    return this.createAccount.create(input, initialBalance);
  }
}
