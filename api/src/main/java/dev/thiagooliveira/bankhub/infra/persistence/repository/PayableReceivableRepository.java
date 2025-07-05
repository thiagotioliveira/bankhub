package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.domain.dto.projection.PayableReceivableEnriched;
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
      value =
          """
    SELECT CAST(pr.id AS VARCHAR) as id,
           CAST(pr.template_id AS VARCHAR) as template_id,
           CAST(prt.account_id AS VARCHAR) as account_id,
           a.name as account_name,
           CAST(prt.category_id AS VARCHAR) as category_id,
           a.currency as currency,
           prt.description as description,
           pr.amount as amount,
           pr.due_date as due_date,
           prt.type as type,
           pr.status as status,
           prt.frequency as frequency,
           pr.installment_number as installment_number,
           prt.installment_total as installment_total,
           CAST(pr.transaction_id AS VARCHAR) as payment_id
               FROM payables_receivables pr
               INNER JOIN payable_receivable_templates prt ON pr.template_id = prt.id
               INNER JOIN accounts a ON prt.account_id = a.id
               WHERE a.id = :accountId
               AND pr.due_date >= :startDate AND pr.due_date <= :endDate
            ORDER BY pr.due_date ASC
""",
      nativeQuery = true)
  List<PayableReceivableEnriched> findByAccountIdOrderByDueDateAsc(
      @Param("accountId") UUID accountId,
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
