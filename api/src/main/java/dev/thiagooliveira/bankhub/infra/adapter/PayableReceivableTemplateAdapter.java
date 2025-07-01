package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableTemplateInput;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableTemplate;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivableTemplatePort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.PayableReceivableTemplateEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.PayableReceivableTemplateRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PayableReceivableTemplateAdapter implements PayableReceivableTemplatePort {

  private final PayableReceivableTemplateRepository payableReceivableTemplateRepository;

  public PayableReceivableTemplateAdapter(
      PayableReceivableTemplateRepository payableReceivableTemplateRepository) {
    this.payableReceivableTemplateRepository = payableReceivableTemplateRepository;
  }

  @Override
  public PayableReceivableTemplate create(CreatePayableReceivableTemplateInput input) {
    return this.payableReceivableTemplateRepository
        .save(PayableReceivableTemplateEntity.from(input))
        .toDomain();
  }

  @Override
  public List<PayableReceivableTemplate>
      findByOrganizationIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
          UUID organizationId, LocalDate from, LocalDate to) {
    return this.payableReceivableTemplateRepository
        .findByOrganizationIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
            organizationId, from, to)
        .stream()
        .map(PayableReceivableTemplateEntity::toDomain)
        .toList();
  }
}
