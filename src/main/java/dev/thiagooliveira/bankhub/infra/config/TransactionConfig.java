package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.*;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import dev.thiagooliveira.bankhub.domain.port.TransactionPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {

  @Bean
  public CreateTransaction createTransaction(
      TransactionPort transactionPort,
      AccountPort accountPort,
      GetCategory getCategory,
      GetAccount getAccount) {
    return new CreateTransaction(transactionPort, accountPort, getCategory, getAccount);
  }

  @Bean
  public GetTransaction getTransaction(TransactionPort transactionPort) {
    return new GetTransaction(transactionPort);
  }
}
