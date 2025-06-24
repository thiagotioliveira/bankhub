package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.CreateTransaction;
import dev.thiagooliveira.bankhub.application.usecase.GetCategory;
import dev.thiagooliveira.bankhub.application.usecase.GetTransaction;
import dev.thiagooliveira.bankhub.domain.port.TransactionPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {

  @Bean
  public CreateTransaction createTransaction(
      TransactionPort transactionPort, GetCategory getCategory) {
    return new CreateTransaction(transactionPort, getCategory);
  }

  @Bean
  public GetTransaction getTransaction(TransactionPort transactionPort) {
    return new GetTransaction(transactionPort);
  }
}
