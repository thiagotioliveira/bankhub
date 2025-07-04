package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.*;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import dev.thiagooliveira.bankhub.infra.service.PayableReceivableService;
import dev.thiagooliveira.bankhub.infra.service.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

  @Bean
  public CreateAccount createAccount(
      AccountPort accountPort,
      GetBank getBank,
      CreateTransaction createTransaction,
      GetCategory getCategory,
      CreateMonthlyAccountSummary createMonthlyAccountSummary) {
    return new CreateAccount(
        accountPort, getBank, createTransaction, getCategory, createMonthlyAccountSummary);
  }

  @Bean
  public GetAccount getAccount(AccountPort accountPort) {
    return new GetAccount(accountPort);
  }

  @Bean
  public CreateMonthlyAccountSummary createAccountBalanceSnapshot(AccountPort accountPort) {
    return new CreateMonthlyAccountSummary(accountPort);
  }

  @Bean
  public GetMonthlyAccountSummary getMonthlyAccountSummary(
      TransactionService transactionService,
      PayableReceivableService payableReceivableService,
      AccountPort accountPort) {
    return new GetMonthlyAccountSummary(transactionService, payableReceivableService, accountPort);
  }
}
