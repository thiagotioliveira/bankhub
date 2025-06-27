package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import java.util.List;

public class CreatePayableReceivable {

  private final PayableReceivablePort port;

  public CreatePayableReceivable(PayableReceivablePort port) {
    this.port = port;
  }

  public List<PayableReceivable> create(CreatePayableReceivableEnrichedInput input) {
    return this.port.create(input);
  }
}
