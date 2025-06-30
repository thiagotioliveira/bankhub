package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.*;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PayableReceivablePort {

  PayableReceivable update(PayableReceivable payableReceivable);

  Optional<PayableReceivable> findByIdAndAccountId(UUID id, UUID accountId);

  PayableReceivable create(CreatePayableReceivableEnrichedInput input);

  Optional<PayableReceivable> findByIdAndOrganizationId(UUID id, UUID organizationId);

  List<PayableReceivable> findByOrganizationId(UUID organizationId);
}
