package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.*;

public interface PayableReceivablePort {

  PayableReceivable create(CreatePayableReceivableEnrichedInput input);

  Page<PayableReceivable> findByAccountId(Long accountId, Pageable pageable);
}
