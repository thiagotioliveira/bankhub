package dev.thiagooliveira.bankhub.domain.dto;

import java.util.UUID;

public record CreateOrganizationInput(String ownerName, String ownerEmail, String ownerPassword) {

  public CreateUserInput toUserRegistrationInput(UUID organizationId) {
    return new CreateUserInput(this.ownerName, this.ownerEmail, this.ownerPassword, organizationId);
  }
}
