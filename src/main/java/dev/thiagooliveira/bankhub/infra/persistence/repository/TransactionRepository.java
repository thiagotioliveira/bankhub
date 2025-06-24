package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.TransactionEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository
    extends JpaRepository<TransactionEntity, UUID>,
        PagingAndSortingRepository<TransactionEntity, UUID> {
  Page<TransactionEntity> findByAccountId(UUID accountId, Pageable pageable);
}
