package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.*;
import dev.thiagooliveira.bankhub.domain.dto.projection.PayableReceivableEnriched;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PayableReceivablePort {

  PayableReceivable update(UUID id, Optional<BigDecimal> amount, Optional<LocalDate> dueDate);

  PayableReceivable update(PayableReceivable payableReceivable);

  Optional<PayableReceivable> findByIdAndAccountId(UUID id, UUID accountId);

  PayableReceivable create(CreatePayableReceivableEnrichedInput input);

  Optional<PayableReceivable> findByIdAndOrganizationId(UUID id, UUID organizationId);

  List<PayableReceivableEnriched> findByOrganizationId(
      UUID organizationId, LocalDate from, LocalDate to);

  @Deprecated
  List<PayableReceivable> findByTemplateIdInAndDueDateBetween(
      List<UUID> templateIds, LocalDate from, LocalDate to);

  Optional<PayableReceivable> findByTemplateIdAndDueDate(UUID templateId, LocalDate dueDate);

  boolean existsByTemplateIdAndDueDate(UUID templateId, LocalDate dueDate);
}
