package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.model.Bank;
import dev.thiagooliveira.bankhub.domain.port.BankPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetBank {

  private final BankPort bankPort;

  public GetBank(BankPort bankPort) {
    this.bankPort = bankPort;
  }

  public Optional<Bank> findById(UUID id, UUID organizationId) {
    return this.bankPort.findById(id, organizationId);
  }

  public boolean existsByIdAndOrganizationId(UUID id, UUID organizationId) {
    return this.bankPort.existsByIdAndOrganizationId(id, organizationId);
  }

  public List<Bank> findByOrganizationId(UUID organizationId) {
    return this.bankPort.findByOrganizationId(organizationId);
  }
}
