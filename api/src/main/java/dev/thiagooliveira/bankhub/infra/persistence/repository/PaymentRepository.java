package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.PaymentEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
  List<PaymentEntity> findByPayableReceivableId(UUID payableReceivableId);
}
