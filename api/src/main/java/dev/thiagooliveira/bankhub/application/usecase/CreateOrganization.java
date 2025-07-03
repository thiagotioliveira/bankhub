package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationOutput;
import dev.thiagooliveira.bankhub.domain.port.OrganizationPort;

public class CreateOrganization {

  private final OrganizationPort organizationPort;
  private final CreateUser createUser;

  public CreateOrganization(OrganizationPort organizationPort, CreateUser createUser) {
    this.organizationPort = organizationPort;
    this.createUser = createUser;
  }

  public CreateOrganizationOutput create(CreateOrganizationInput input) {
    var org = this.organizationPort.create(input);
    var user = this.createUser.create(input.toUserRegistrationInput(org.id()));
    return new CreateOrganizationOutput(org.id(), org.createdAt(), user.id());
  }
}
