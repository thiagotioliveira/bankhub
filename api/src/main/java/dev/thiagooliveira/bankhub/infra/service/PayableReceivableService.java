package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreatePayableReceivable;
import dev.thiagooliveira.bankhub.application.usecase.GetPayableReceivable;
import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput;
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

  public PayableReceivableService(
      CreatePayableReceivable createPayableReceivable, GetPayableReceivable getPayableReceivable) {
    this.createPayableReceivable = createPayableReceivable;
    this.getPayableReceivable = getPayableReceivable;
  }

  @Transactional
  public PayableReceivable create(CreatePayableReceivableInput input) {
    return this.createPayableReceivable.create(input);
  }

  @Transactional
  public List<PayableReceivable> getPayableReceivables(
      UUID organizationId, LocalDate from, LocalDate to) {
    return this.getPayableReceivable.findByOrganizationId(organizationId, from, to);
  }

  public Optional<PayableReceivable> getPayableReceivable(UUID id, UUID organizationId) {
    return this.getPayableReceivable.findByIdAndOrganizationId(id, organizationId);
  }
}
