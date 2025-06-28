package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.*;
import java.util.Optional;
import java.util.UUID;

public interface PayableReceivablePort {

  PayableReceivable update(PayableReceivable payableReceivable);

  Optional<PayableReceivable> findByIdAndAccountId(UUID id, UUID accountId);

  PayableReceivable create(CreatePayableReceivableEnrichedInput input);

  Page<PayableReceivable> findByAccountId(UUID accountId, Pageable pageable);
}
