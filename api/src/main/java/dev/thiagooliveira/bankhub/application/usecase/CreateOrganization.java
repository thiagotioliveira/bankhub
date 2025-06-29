package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationOutput;
import dev.thiagooliveira.bankhub.domain.port.OrganizationPort;
import dev.thiagooliveira.bankhub.domain.port.UserPort;

public class CreateOrganization {

  private final OrganizationPort organizationPort;
  private final UserPort userPort;

  public CreateOrganization(OrganizationPort organizationPort, UserPort userPort) {
    this.organizationPort = organizationPort;
    this.userPort = userPort;
  }

  public CreateOrganizationOutput create(CreateOrganizationInput input) {
    var org = this.organizationPort.create(input);
    var user = this.userPort.create(input.toUserRegistrationInput(org.id()));
    return new CreateOrganizationOutput(org.id(), org.createdAt(), user.id());
  }
}
