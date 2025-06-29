package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.domain.dto.projection.TransactionEnriched;
import dev.thiagooliveira.bankhub.infra.persistence.entity.TransactionEntity;
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
            t.date_time AS dateTime,
            t.description AS description,
            c.id AS categoryId,
            c.name AS categoryName,
            c.type AS categoryType,
            t.amount AS amount
        FROM transactions t
        JOIN categories c ON t.category_id = c.id
        WHERE t.account_id = :accountId
        ORDER BY t.date_time DESC
        """,
      countQuery =
          """
        SELECT COUNT(*)
        FROM transactions t
        JOIN categories c ON t.category_id = c.id
        WHERE t.account_id = :accountId
        """,
      nativeQuery = true)
  Page<TransactionEnriched> findByAccountIdOrderByDateTimeDescEnriched(
      @Param("accountId") UUID accountId, Pageable pageable);
}
