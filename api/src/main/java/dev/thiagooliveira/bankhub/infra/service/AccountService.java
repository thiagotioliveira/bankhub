package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateAccount;
import dev.thiagooliveira.bankhub.application.usecase.GetAccount;
import dev.thiagooliveira.bankhub.application.usecase.GetMonthlyAccountSummary;
import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.dto.projection.AccountEnriched;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.model.MonthlyAccountSummary;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

  private final CreateAccount createAccount;
  private final GetAccount getAccount;
  private final GetMonthlyAccountSummary getMonthlyAccountSummary;

  public AccountService(
      CreateAccount createAccount,
      GetAccount getAccount,
      GetMonthlyAccountSummary getMonthlyAccountSummary) {
    this.createAccount = createAccount;
    this.getAccount = getAccount;
    this.getMonthlyAccountSummary = getMonthlyAccountSummary;
  }

  @Transactional
  public Account create(CreateAccountInput input, BigDecimal initialBalance) {
    return this.createAccount.create(input, initialBalance);
  }

  public Optional<AccountEnriched> findByIdAndOrganizationIdEnriched(UUID id, UUID organizationId) {
    return this.getAccount.findByIdAndOrganizationIdEnriched(id, organizationId);
  }

  public List<Account> findByOrganizationId(UUID organizationId) {
    return this.getAccount.findByOrganizationId(organizationId);
  }

  public List<MonthlyAccountSummary> getMonthlyAccountSummary(
      UUID organizationId, YearMonth month) {
    return this.getMonthlyAccountSummary.get(organizationId, month);
  }
}
