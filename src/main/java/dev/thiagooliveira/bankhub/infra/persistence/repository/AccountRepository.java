package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.AccountEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

  Optional<AccountEntity> findByIdAndOrganizationId(UUID id, UUID organizationId);

  boolean existsByNameIgnoreCaseAndOrganizationId(String name, UUID organizationId);
}
