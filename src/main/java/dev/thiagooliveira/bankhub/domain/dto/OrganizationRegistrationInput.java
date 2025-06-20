package dev.thiagooliveira.bankhub.domain.dto;

import java.util.UUID;

public record OrganizationRegistrationInput(String ownerName, String ownerEmail) {

  public UserRegistrationInput toUserRegistrationInput(UUID organizationId) {
    return new UserRegistrationInput(this.ownerName(), this.ownerEmail, organizationId);
  }
}
