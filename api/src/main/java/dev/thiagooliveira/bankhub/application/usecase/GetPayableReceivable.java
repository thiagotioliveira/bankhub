package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.model.PayableReceivable;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetPayableReceivable {

  private final PayableReceivablePort port;

  public GetPayableReceivable(PayableReceivablePort port) {
    this.port = port;
  }

  public List<PayableReceivable> findByOrganizationId(UUID organizationId) {
    return port.findByOrganizationId(organizationId);
  }

  public Optional<PayableReceivable> findByIdAndOrganizationId(UUID id, UUID organizationId) {
    return this.port.findByIdAndOrganizationId(id, organizationId);
  }
}
