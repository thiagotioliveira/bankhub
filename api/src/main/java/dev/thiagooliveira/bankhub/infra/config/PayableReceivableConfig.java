package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.*;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayableReceivableConfig {

  @Bean
  public CreatePayableReceivable createReceivable(
      PayableReceivablePort port, GetCategory getCategory) {
    return new CreatePayableReceivable(port, getCategory);
  }

  @Bean
  public ConfirmPayment confirmPayment(
      PayableReceivablePort payableReceivablePort,
      GetAccount getAccount,
      CreateTransaction createTransaction) {
    return new ConfirmPayment(payableReceivablePort, getAccount, createTransaction);
  }
}
