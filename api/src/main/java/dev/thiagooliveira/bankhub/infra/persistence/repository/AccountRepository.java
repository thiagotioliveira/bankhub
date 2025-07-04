package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.domain.dto.projection.AccountEnriched;
import dev.thiagooliveira.bankhub.infra.persistence.entity.AccountEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

  List<AccountEntity> findByOrganizationId(UUID organizationId);

  Optional<AccountEntity> findByIdAndOrganizationId(UUID id, UUID organizationId);

  @Query(
      value =
          """
  SELECT
    CAST(a.id AS VARCHAR) AS id,
    a.name AS name,
    a.balance AS balance,
    a.currency AS currency,
    CAST(b.id AS VARCHAR) AS bankId,
    b.name AS bankName
  FROM accounts a
  JOIN banks b ON a.bank_id = b.id
""",
      nativeQuery = true)
  Optional<AccountEnriched> findByIdAndOrganizationIdEnriched(UUID id, UUID organizationId);

  boolean existsByNameIgnoreCaseAndOrganizationId(String name, UUID organizationId);
}
