package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateReceivableEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.Receivable;
import java.util.List;

public interface PayableReceivablePort {

  List<Receivable> createReceivable(CreateReceivableEnrichedInput input);
}
