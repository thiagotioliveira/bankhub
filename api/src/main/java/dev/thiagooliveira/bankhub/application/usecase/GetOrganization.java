package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.model.Organization;
import dev.thiagooliveira.bankhub.domain.port.OrganizationPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetOrganization {

  private final OrganizationPort organizationPort;

  public GetOrganization(OrganizationPort organizationPort) {
    this.organizationPort = organizationPort;
  }

  public List<Organization> list(UUID organizationId) {
    return this.organizationPort.findById(organizationId).map(List::of).orElseGet(List::of);
  }

  public Optional<Organization> findById(UUID organizationId) {
    return this.organizationPort.findById(organizationId);
  }
}
