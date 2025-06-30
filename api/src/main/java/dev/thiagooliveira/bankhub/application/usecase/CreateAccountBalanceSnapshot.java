package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreateAccountBalanceSnapshot {

  private final AccountPort accountPort;

  public CreateAccountBalanceSnapshot(AccountPort accountPort) {
    this.accountPort = accountPort;
  }

  public void create(UUID id, LocalDate date, BigDecimal balance) {
    this.accountPort.createBalanceSnapshot(id, date, balance);
  }
}
