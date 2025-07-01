package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableTemplateInput;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableTemplate;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PayableReceivableTemplatePort {
  PayableReceivableTemplate create(CreatePayableReceivableTemplateInput input);

  List<PayableReceivableTemplate>
      findByOrganizationIdAndStartDateLessThanEqualOrStartDateBetweenAndRecurringIsTrue(
          UUID organizationId, LocalDate from, LocalDate to);
}
