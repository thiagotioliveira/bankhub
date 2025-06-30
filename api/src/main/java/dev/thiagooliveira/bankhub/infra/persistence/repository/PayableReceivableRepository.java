package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.PayableReceivableEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayableReceivableRepository extends JpaRepository<PayableReceivableEntity, UUID> {
  @Query(
      """
  SELECT pr FROM PayableReceivableEntity pr
  JOIN AccountEntity a ON pr.accountId = a.id
  WHERE a.organizationId = :organizationId
  ORDER BY pr.dueDate ASC
""")
  List<PayableReceivableEntity> findByOrganizationIdOrderByDueDateAsc(
      @Param("organizationId") UUID organizationId);

  @Query(
      """
      SELECT pr FROM PayableReceivableEntity pr
      JOIN AccountEntity a ON pr.accountId = a.id
      WHERE a.organizationId = :organizationId
          AND pr.id = :id
      ORDER BY pr.dueDate ASC
    """)
  Optional<PayableReceivableEntity> findByIdAndOrganizationId(
      @Param("id") UUID id, @Param("organizationId") UUID organizationId);

  Optional<PayableReceivableEntity> findByIdAndAccountId(UUID id, UUID accountId);
}
