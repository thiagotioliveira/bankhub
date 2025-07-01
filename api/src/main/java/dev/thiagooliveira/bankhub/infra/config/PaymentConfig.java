package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.CreatePayment;
import dev.thiagooliveira.bankhub.application.usecase.CreateTransaction;
import dev.thiagooliveira.bankhub.application.usecase.GetAccount;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import dev.thiagooliveira.bankhub.domain.port.PaymentPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

  @Bean
  public CreatePayment createPayment(
      PayableReceivablePort payableReceivablePort,
      GetAccount getAccount,
      CreateTransaction createTransaction,
      PaymentPort paymentPort) {
    return new CreatePayment(payableReceivablePort, getAccount, createTransaction, paymentPort);
  }
}
