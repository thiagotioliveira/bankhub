package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.OrganizationRegistration;
import dev.thiagooliveira.bankhub.domain.dto.OrganizationRegistrationInput;
import dev.thiagooliveira.bankhub.domain.dto.OrganizationRegistrationOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationService {

  private final OrganizationRegistration organizationRegistration;

  public OrganizationService(OrganizationRegistration organizationRegistration) {
    this.organizationRegistration = organizationRegistration;
  }

  @Transactional
  public OrganizationRegistrationOutput register(OrganizationRegistrationInput input) {
    return this.organizationRegistration.perform(input);
  }
}
