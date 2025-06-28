package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateBankInput;
import dev.thiagooliveira.bankhub.domain.model.Bank;
import dev.thiagooliveira.bankhub.domain.port.BankPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.BankEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.BankRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BankAdapter implements BankPort {

  private final BankRepository bankRepository;

  public BankAdapter(BankRepository bankRepository) {
    this.bankRepository = bankRepository;
  }

  @Override
  public Bank create(CreateBankInput input) {
    return this.bankRepository.save(BankEntity.from(input)).toDomain();
  }

  @Override
  public Optional<Bank> findById(UUID id) {
    return this.bankRepository.findById(id).map(BankEntity::toDomain);
  }

  @Override
  public boolean existsByIdAndOrganizationId(UUID id, UUID organizationId) {
    return this.bankRepository.existsByIdAndOrganizationId(id, organizationId);
  }

  @Override
  public boolean existsByNameIgnoreCaseAndOrganizationId(String name, UUID organizationId) {
    return this.bankRepository.existsByNameIgnoreCaseAndOrganizationId(name, organizationId);
  }
}
