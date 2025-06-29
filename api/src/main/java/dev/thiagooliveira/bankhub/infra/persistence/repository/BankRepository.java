package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.BankEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<BankEntity, UUID> {

  List<BankEntity> findByOrganizationId(UUID organizationId);

  Optional<BankEntity> findByIdAndOrganizationId(UUID id, UUID organizationId);

  boolean existsByIdAndOrganizationId(UUID id, UUID organizationId);

  boolean existsByNameIgnoreCaseAndOrganizationId(String name, UUID organizationId);
}
