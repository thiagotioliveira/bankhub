package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.ConfirmPayment;
import dev.thiagooliveira.bankhub.application.usecase.CreatePayableReceivable;
import dev.thiagooliveira.bankhub.application.usecase.CreateTransaction;
import dev.thiagooliveira.bankhub.application.usecase.GetAccount;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayableReceivableConfig {

  @Bean
  public CreatePayableReceivable createReceivable(PayableReceivablePort port) {
    return new CreatePayableReceivable(port);
  }

  @Bean
  public ConfirmPayment confirmPayment(
      PayableReceivablePort payableReceivablePort,
      GetAccount getAccount,
      CreateTransaction createTransaction) {
    return new ConfirmPayment(payableReceivablePort, getAccount, createTransaction);
  }
}
