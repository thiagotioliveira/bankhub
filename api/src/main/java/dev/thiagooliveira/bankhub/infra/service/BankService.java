package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateBank;
import dev.thiagooliveira.bankhub.application.usecase.GetBank;
import dev.thiagooliveira.bankhub.domain.dto.CreateBankInput;
import dev.thiagooliveira.bankhub.domain.model.Bank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class BankService {

  private final CreateBank createBank;
  private final GetBank getBank;

  public BankService(CreateBank createBank, GetBank getBank) {
    this.createBank = createBank;
    this.getBank = getBank;
  }

  public Bank create(CreateBankInput input) {
    return this.createBank.create(input);
  }

  public Optional<Bank> findById(UUID id, UUID organizationId) {
    return this.getBank.findById(id, organizationId);
  }

  public List<Bank> findByOrganizationId(UUID organizationId) {
    return this.getBank.findByOrganizationId(organizationId);
  }
}
