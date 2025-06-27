package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.PayableReceivableEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayableReceivableRepository extends JpaRepository<PayableReceivableEntity, UUID> {}
