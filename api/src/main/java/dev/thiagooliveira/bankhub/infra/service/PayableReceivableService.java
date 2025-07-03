package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreatePayableReceivable;
import dev.thiagooliveira.bankhub.application.usecase.GetPayableReceivable;
import dev.thiagooliveira.bankhub.application.usecase.UpdatePayableReceivable;
import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.dto.UpdatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.dto.projection.PayableReceivableEnriched;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayableReceivableService {

  private final CreatePayableReceivable createPayableReceivable;
  private final GetPayableReceivable getPayableReceivable;
  private final UpdatePayableReceivable updatePayableReceivable;

  public PayableReceivableService(
      CreatePayableReceivable createPayableReceivable,
      GetPayableReceivable getPayableReceivable,
      UpdatePayableReceivable updatePayableReceivable) {
    this.createPayableReceivable = createPayableReceivable;
    this.getPayableReceivable = getPayableReceivable;
    this.updatePayableReceivable = updatePayableReceivable;
  }

  @Transactional
  public void update(UpdatePayableReceivableInput input) {
    this.updatePayableReceivable.update(input);
  }

  @Transactional
  public PayableReceivable create(CreatePayableReceivableInput input) {
    return this.createPayableReceivable.create(input);
  }

  @Transactional
  public List<PayableReceivableEnriched> getPayableReceivables(
      UUID organizationId, LocalDate from, LocalDate to) {
    return this.getPayableReceivable.findByOrganizationId(organizationId, from, to);
  }

  public Optional<PayableReceivable> getPayableReceivable(UUID id, UUID organizationId) {
    return this.getPayableReceivable.findByIdAndOrganizationId(id, organizationId);
  }

  public List<PayableReceivableEnriched> findByOrganizationId(
      UUID organizationId, LocalDate from, LocalDate to) {
    return this.getPayableReceivable.findByOrganizationId(organizationId, from, to);
  }
}
