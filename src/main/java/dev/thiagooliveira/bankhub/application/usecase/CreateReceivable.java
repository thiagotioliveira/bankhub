package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateReceivableEnrichedInput;
import dev.thiagooliveira.bankhub.domain.dto.Receivable;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import java.util.List;

public class CreateReceivable {

  private final PayableReceivablePort port;

  public CreateReceivable(PayableReceivablePort port) {
    this.port = port;
  }

  public List<Receivable> create(CreateReceivableEnrichedInput input) {
    return this.port.createReceivable(input);
  }
}
