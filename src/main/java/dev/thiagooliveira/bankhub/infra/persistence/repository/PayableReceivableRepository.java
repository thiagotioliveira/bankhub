package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.PayableReceivableEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PayableReceivableRepository
    extends JpaRepository<PayableReceivableEntity, UUID>,
        PagingAndSortingRepository<PayableReceivableEntity, UUID> {
  Page<PayableReceivableEntity> findByAccountId(UUID accountId, Pageable pageable);
}
