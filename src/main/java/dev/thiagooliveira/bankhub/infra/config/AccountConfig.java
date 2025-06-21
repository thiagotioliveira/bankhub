package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.CreateAccount;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

  @Bean
  public CreateAccount createAccount(AccountPort accountPort) {
    return new CreateAccount(accountPort);
  }
}
