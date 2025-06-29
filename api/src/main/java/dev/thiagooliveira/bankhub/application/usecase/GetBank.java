package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.model.Bank;
import dev.thiagooliveira.bankhub.domain.port.BankPort;
import java.util.Optional;
import java.util.UUID;

public class GetBank {

  private final BankPort bankPort;

  public GetBank(BankPort bankPort) {
    this.bankPort = bankPort;
  }

  public Optional<Bank> findById(UUID id) {
    return this.bankPort.findById(id);
  }

  public boolean existsByIdAndOrganizationId(UUID id, UUID organizationId) {
    return this.bankPort.existsByIdAndOrganizationId(id, organizationId);
  }
}
