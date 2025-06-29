package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.dto.projection.AccountEnriched;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.AccountEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.AccountRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AccountAdapter implements AccountPort {

  private final AccountRepository accountRepository;

  public AccountAdapter(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
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
}
