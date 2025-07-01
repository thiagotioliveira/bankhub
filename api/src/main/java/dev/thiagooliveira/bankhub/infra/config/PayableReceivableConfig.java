package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.*;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivableTemplatePort;
import dev.thiagooliveira.bankhub.domain.port.PaymentPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayableReceivableConfig {

  @Bean
  public CreatePayableReceivable createReceivable(
      PayableReceivablePort port,
      PayableReceivableTemplatePort templatePort,
      GetCategory getCategory,
      GetAccount getAccount) {
    return new CreatePayableReceivable(port, templatePort, getCategory, getAccount);
  }

  @Bean
  public ConfirmPayment confirmPayment(
      PayableReceivablePort payableReceivablePort,
      GetAccount getAccount,
      CreateTransaction createTransaction,
      PaymentPort paymentPort) {
    return new ConfirmPayment(payableReceivablePort, getAccount, createTransaction, paymentPort);
  }

  @Bean
  public GetPayableReceivable getPayableReceivable(
      PayableReceivablePort payableReceivablePort,
      PayableReceivableTemplatePort payableReceivableTemplatePort,
      CreatePayableReceivable createPayableReceivable) {
    return new GetPayableReceivable(
        payableReceivablePort, payableReceivableTemplatePort, createPayableReceivable);
  }
}
