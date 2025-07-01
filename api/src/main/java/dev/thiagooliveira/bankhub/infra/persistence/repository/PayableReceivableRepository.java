package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.PayableReceivableEntity;
import java.time.LocalDate;
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
  JOIN PayableReceivableTemplateEntity t ON pr.templateId = t.id
  JOIN AccountEntity a ON t.accountId = a.id
  WHERE a.organizationId = :organizationId
  AND pr.dueDate >= :startDate
  AND pr.dueDate <= :endDate
  ORDER BY pr.dueDate ASC
""")
  List<PayableReceivableEntity> findByOrganizationIdOrderByDueDateAsc(
      @Param("organizationId") UUID organizationId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  @Query(
      """
      SELECT pr FROM PayableReceivableEntity pr
      JOIN PayableReceivableTemplateEntity t ON pr.templateId = t.id
      JOIN AccountEntity a ON t.accountId = a.id
      WHERE a.organizationId = :organizationId
          AND pr.id = :id
      ORDER BY pr.dueDate ASC
    """)
  Optional<PayableReceivableEntity> findByIdAndOrganizationId(
      @Param("id") UUID id, @Param("organizationId") UUID organizationId);

  @Query(
      """
          SELECT pr FROM PayableReceivableEntity pr
          JOIN PayableReceivableTemplateEntity t ON pr.templateId = t.id
          WHERE t.accountId = :accountId
              AND pr.id = :id
          ORDER BY pr.dueDate ASC
        """)
  Optional<PayableReceivableEntity> findByIdAndAccountId(
      @Param("id") UUID id, @Param("accountId") UUID accountId);

  List<PayableReceivableEntity> findByTemplateIdInAndDueDateBetween(
      List<UUID> templateId, LocalDate from, LocalDate to);

  Optional<PayableReceivableEntity> findByTemplateIdAndDueDate(UUID templateId, LocalDate dueDate);

  boolean existsByTemplateIdAndDueDateOriginal(UUID templateId, LocalDate dueDate);
}
