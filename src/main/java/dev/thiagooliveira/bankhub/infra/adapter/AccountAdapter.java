package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateAccountInput;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.AccountEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountAdapter implements AccountPort {

  private final AccountRepository accountRepository;

  public AccountAdapter(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Account create(CreateAccountInput input) {
    return this.accountRepository.save(AccountEntity.from(input)).toDomain();
  }
}
