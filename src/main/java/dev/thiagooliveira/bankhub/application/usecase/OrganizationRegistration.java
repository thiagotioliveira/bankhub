package dev.thiagooliveira.bankhub.application.usecase;

import dev.thiagooliveira.bankhub.domain.dto.OrganizationRegistrationInput;
import dev.thiagooliveira.bankhub.domain.dto.OrganizationRegistrationOutput;
import dev.thiagooliveira.bankhub.domain.port.OrganizationPort;
import dev.thiagooliveira.bankhub.domain.port.UserPort;

public class OrganizationRegistration {

  private final OrganizationPort organizationPort;
  private final UserPort userPort;

  public OrganizationRegistration(OrganizationPort organizationPort, UserPort userPort) {
    this.organizationPort = organizationPort;
    this.userPort = userPort;
  }

  public OrganizationRegistrationOutput perform(OrganizationRegistrationInput input) {
    var org = this.organizationPort.create(input);
    var user = this.userPort.create(input.toUserRegistrationInput(org.id()));
    return new OrganizationRegistrationOutput(org.id(), org.createdAt(), user.id());
  }
}
