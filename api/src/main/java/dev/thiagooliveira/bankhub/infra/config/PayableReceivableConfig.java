package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.*;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivableTemplatePort;
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
  public CreatePayment confirmPayment(
      PayableReceivablePort payableReceivablePort,
      GetAccount getAccount,
      CreateTransaction createTransaction) {
    return new CreatePayment(payableReceivablePort, getAccount, createTransaction);
  }

  @Bean
  public GetPayableReceivable getPayableReceivable(
      PayableReceivablePort payableReceivablePort,
      PayableReceivableTemplatePort payableReceivableTemplatePort,
      CreatePayableReceivable createPayableReceivable) {
    return new GetPayableReceivable(
        payableReceivablePort, payableReceivableTemplatePort, createPayableReceivable);
  }

  @Bean
  public UpdatePayableReceivable updatePayableReceivable(
      PayableReceivablePort payableReceivablePort) {
    return new UpdatePayableReceivable(payableReceivablePort);
  }
}
