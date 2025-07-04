package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.MonthlyAccountSummaryEntity;
import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyAccountSummaryRepository
    extends JpaRepository<MonthlyAccountSummaryEntity, UUID> {

  Optional<MonthlyAccountSummaryEntity> findByAccountIdAndYearMonth(
      UUID accountId, YearMonth targetMonth);

  Optional<MonthlyAccountSummaryEntity> findTopByAccountIdOrderByYearMonthDesc(UUID accountId);
}
