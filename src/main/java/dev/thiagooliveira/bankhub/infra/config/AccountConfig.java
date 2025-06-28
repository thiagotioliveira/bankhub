package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.CreateAccount;
import dev.thiagooliveira.bankhub.application.usecase.CreateTransaction;
import dev.thiagooliveira.bankhub.application.usecase.GetBank;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

  @Bean
  public CreateAccount createAccount(
      AccountPort accountPort, GetBank getBank, CreateTransaction createTransaction) {
    return new CreateAccount(accountPort, getBank, createTransaction);
  }
}
