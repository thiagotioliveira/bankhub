package dev.thiagooliveira.bankhub.infra.persistence.repository;

import dev.thiagooliveira.bankhub.infra.persistence.entity.PayableReceivableTemplateEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayableReceivableTemplateRepository
    extends JpaRepository<PayableReceivableTemplateEntity, UUID> {

  @Query(
      """
    SELECT prt FROM PayableReceivableTemplateEntity prt
  WHERE prt.accountId = :accountId
  AND (prt.startDate <= :startDate OR (prt.startDate >= :startDate AND prt.startDate <= :endDate))
  AND prt.recurring is true
""")
  List<PayableReceivableTemplateEntity>
      findByAccountIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
          @Param("accountId") UUID accountId,
          @Param("startDate") LocalDate startDate,
          @Param("endDate") LocalDate endDate);
}
