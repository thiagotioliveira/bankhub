package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.model.*;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;

public class CreateMonthlyAccountSummary {

  private final AccountPort accountPort;

  public CreateMonthlyAccountSummary(AccountPort accountPort) {
    this.accountPort = accountPort;
  }

  public void create(MonthlyAccountSummary summary) {
    this.accountPort.createMonthlyAccountSummary(summary);
  }
}
