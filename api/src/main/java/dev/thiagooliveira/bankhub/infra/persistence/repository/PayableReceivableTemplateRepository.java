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
  JOIN AccountEntity a ON prt.accountId = a.id
  WHERE a.organizationId = :organizationId
  AND (prt.startDate <= :startDate OR (prt.startDate >= :startDate AND prt.startDate <= :endDate))
  AND prt.recurring is true
""")
  List<PayableReceivableTemplateEntity>
      findByOrganizationIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
          @Param("organizationId") UUID organizationId,
          @Param("startDate") LocalDate startDate,
          @Param("endDate") LocalDate endDate);
}
