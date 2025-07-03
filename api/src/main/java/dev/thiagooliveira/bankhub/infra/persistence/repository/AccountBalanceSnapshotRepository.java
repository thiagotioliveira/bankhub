package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.MonthlyAccountSummaryEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBalanceSnapshotRepository
    extends JpaRepository<MonthlyAccountSummaryEntity, UUID> {}
