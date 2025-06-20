package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.OrganizationRegistrationInput;
import dev.thiagooliveira.bankhub.domain.model.Organization;

public interface OrganizationPort {
  Organization create(OrganizationRegistrationInput input);
}
