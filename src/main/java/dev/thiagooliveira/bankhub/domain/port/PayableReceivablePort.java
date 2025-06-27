package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.PayableReceivable;
import java.util.List;

public interface PayableReceivablePort {

  List<PayableReceivable> create(CreatePayableReceivableEnrichedInput input);
}
