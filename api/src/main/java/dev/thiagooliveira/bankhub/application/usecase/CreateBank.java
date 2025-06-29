package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateBankInput;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Bank;
import dev.thiagooliveira.bankhub.domain.port.BankPort;

public class CreateBank {

  private final BankPort bankPort;

  public CreateBank(BankPort bankPort) {
    this.bankPort = bankPort;
  }

  public Bank create(CreateBankInput input) {
    if (this.bankPort.existsByNameIgnoreCaseAndOrganizationId(
        input.name(), input.organizationId())) {
      throw BusinessLogicException.badRequest("the name already exists");
    }
    return this.bankPort.create(input);
  }
}
