package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.bankhub.infra.persistence.entity.TransactionEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository
    extends JpaRepository<TransactionEntity, UUID>,
        PagingAndSortingRepository<TransactionEntity, UUID> {

  @Query(
      value =
          """
        SELECT
            t.id AS id,
            t.account_id as accountId,
            a.currency AS currency,
            t.date_time AS dateTime,
            t.description AS description,
            c.id AS categoryId,
            c.name AS categoryName,
            c.type AS categoryType,
            t.amount AS amount
        FROM transactions t
        JOIN categories c ON t.category_id = c.id
        JOIN accounts a ON a.id = t.account_id
        WHERE t.account_id IN :accountIds
          AND a.organization_id = :organizationId
        ORDER BY t.date_time DESC
        LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}
        """,
      countQuery =
          """
        SELECT COUNT(*)
        FROM transactions t
        JOIN categories c ON t.category_id = c.id
        JOIN accounts a ON a.id = t.account_id
        WHERE t.account_id IN :accountIds
          AND a.organization_id = :organizationId
        """,
      nativeQuery = true)
  Page<TransactionEnriched> findEnrichedByFiltersOrderByDateTime(
      @Param("accountIds") List<UUID> accountIds,
      @Param("organizationId") UUID organizationId,
      Pageable pageable);

  @Query(
      value =
          """
    SELECT
            t.id AS id,
            t.account_id as accountId,
            a.currency AS currency,
            t.date_time AS dateTime,
            t.description AS description,
            c.id AS categoryId,
            c.name AS categoryName,
            c.type AS categoryType,
            t.amount AS amount
        FROM transactions t
        JOIN categories c ON t.category_id = c.id
        JOIN accounts a ON a.id = t.account_id
          AND a.organization_id = :organizationId
          AND t.date_time >= :from
          AND t.date_time <= :to
        ORDER BY t.date_time DESC
""",
      nativeQuery = true)
  List<TransactionEnriched> findByOrganizationIdOrderByDateTime(
      @Param("organizationId") UUID organizationId,
      @Param("from") LocalDate from,
      @Param("to") LocalDate to);
}
