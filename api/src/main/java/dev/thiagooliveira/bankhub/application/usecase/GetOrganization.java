package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.model.Organization;
import dev.thiagooliveira.bankhub.domain.port.OrganizationPort;
import java.util.List;
import java.util.UUID;

public class GetOrganization {

  private final OrganizationPort organizationPort;

  public GetOrganization(OrganizationPort organizationPort) {
    this.organizationPort = organizationPort;
  }

  public List<Organization> list(UUID organizationId) {
    return this.organizationPort.list(organizationId);
  }
}
