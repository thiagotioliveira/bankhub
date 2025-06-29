package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.projection.AccountEnriched;
import dev.thiagooliveira.bankhub.domain.model.Account;
import dev.thiagooliveira.bankhub.domain.port.AccountPort;
import java.util.Optional;
import java.util.UUID;

public class GetAccount {

  private final AccountPort accountPort;

  public GetAccount(AccountPort accountPort) {
    this.accountPort = accountPort;
  }

  public Optional<Account> findByIdAndOrganizationId(UUID id, UUID organizationId) {
    return this.accountPort.findByIdAndOrganizationId(id, organizationId);
  }

  public Optional<AccountEnriched> findByIdAndOrganizationIdEnriched(UUID id, UUID organizationId) {
    return this.accountPort.findByIdAndOrganizationIdEnriched(id, organizationId);
  }
}
