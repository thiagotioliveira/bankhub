package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateBankInput;
import dev.thiagooliveira.bankhub.domain.model.Bank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankPort {

  Bank create(CreateBankInput input);

  Optional<Bank> findById(UUID id, UUID organizationId);

  List<Bank> findByOrganizationId(UUID organizationId);

  boolean existsByIdAndOrganizationId(UUID id, UUID organizationId);

  boolean existsByNameIgnoreCaseAndOrganizationId(String name, UUID organizationId);
}
