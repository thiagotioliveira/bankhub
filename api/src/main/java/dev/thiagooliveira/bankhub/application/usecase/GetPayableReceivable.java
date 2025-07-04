package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.projection.PayableReceivableEnriched;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableTemplate;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivableTemplatePort;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetPayableReceivable {

  private final PayableReceivablePort port;
  private final PayableReceivableTemplatePort templatePort;
  private final CreatePayableReceivable createPayableReceivable;

  public GetPayableReceivable(
      PayableReceivablePort port,
      PayableReceivableTemplatePort templatePort,
      CreatePayableReceivable createPayableReceivable) {
    this.port = port;
    this.templatePort = templatePort;
    this.createPayableReceivable = createPayableReceivable;
  }

  public List<PayableReceivableEnriched> getByAccountIdOrderByDueDateAsc(
      UUID accountId, LocalDate from, LocalDate to) {
    var templates =
        this.templatePort
            .findByAccountIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
                accountId, from, to);
    for (PayableReceivableTemplate template : templates) {
      var dayBase = template.startDate().getDayOfMonth();
      switch (template.frequency().get()) {
        case MONTHLY -> {
          var dueDate = from.withDayOfMonth(dayBase);
          if (!this.port.existsByTemplateIdAndDueDate(template.id(), dueDate)) {
            this.createPayableReceivable.createWithTemplate(template, dueDate);
          }
        }
      }
    }

    return port.findByAccountIdOrderByDueDateAsc(accountId, from, to);
  }

  public Optional<PayableReceivable> findByIdAndOrganizationId(UUID id, UUID organizationId) {
    return this.port.findByIdAndOrganizationId(id, organizationId);
  }
}
