package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.dto.projection.AccountEnriched;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.model.MonthlyAccountSummary;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.AccountEntity;
import dev.thiagooliveira.bankhub.infra.persistence.entity.MonthlyAccountSummaryEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.AccountRepository;
import dev.thiagooliveira.bankhub.infra.persistence.repository.MonthlyAccountSummaryRepository;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AccountAdapter implements AccountPort {

  private final AccountRepository accountRepository;
  private final MonthlyAccountSummaryRepository monthlyAccountSummaryRepository;

  public AccountAdapter(
      AccountRepository accountRepository,
      MonthlyAccountSummaryRepository monthlyAccountSummaryRepository) {
    this.accountRepository = accountRepository;
    this.monthlyAccountSummaryRepository = monthlyAccountSummaryRepository;
  }

  @Override
  public List<Account> findByOrganizationId(UUID organizationId) {
    return this.accountRepository.findByOrganizationId(organizationId).stream()
        .map(AccountEntity::toDomain)
        .toList();
  }

  @Override
  public Optional<Account> findByIdAndOrganizationId(UUID id, UUID organizationId) {
    return this.accountRepository
        .findByIdAndOrganizationId(id, organizationId)
        .map(AccountEntity::toDomain);
  }

  @Override
  public Optional<Account> findById(UUID id) {
    return this.accountRepository.findById(id).map(AccountEntity::toDomain);
  }

  @Override
  public Optional<AccountEnriched> findByIdAndOrganizationIdEnriched(UUID id, UUID organizationId) {
    return this.accountRepository.findByIdAndOrganizationIdEnriched(id, organizationId);
  }

  @Override
  public Account create(CreateAccountInput input) {
    return this.accountRepository.save(AccountEntity.from(input)).toDomain();
  }

  @Override
  public boolean existsByNameIgnoreCaseAndOrganizationId(String name, UUID organizationId) {
    return this.accountRepository.existsByNameIgnoreCaseAndOrganizationId(name, organizationId);
  }

  @Override
  public void update(UUID id, BigDecimal newBalance) {
    var accountEntity = this.accountRepository.findById(id).orElseThrow();
    accountEntity.setBalance(newBalance);
    this.accountRepository.save(accountEntity).toDomain();
  }

  @Override
  public void createMonthlyAccountSummary(MonthlyAccountSummary input) {
    var snapshot =
        new MonthlyAccountSummaryEntity(
            UUID.randomUUID(),
            input.accountId(),
            input.yearMonth(),
            input.balance(),
            input.income(),
            input.expenses(),
            input.receivablesPending(),
            input.payablePending());
    this.monthlyAccountSummaryRepository.save(snapshot);
  }

  @Override
  public Optional<MonthlyAccountSummary> getAccountSummary(UUID accountId, YearMonth targetMonth) {
    return this.monthlyAccountSummaryRepository
        .findByAccountIdAndYearMonth(accountId, targetMonth)
        .map(MonthlyAccountSummaryEntity::toDomain);
  }

  @Override
  public Optional<MonthlyAccountSummary> getLastAccountSummary(UUID accountId) {
    return this.monthlyAccountSummaryRepository
        .findTopByAccountIdOrderByYearMonthDesc(accountId)
        .map(MonthlyAccountSummaryEntity::toDomain);
  }
}
