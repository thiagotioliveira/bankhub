package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateOrganization;
import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationService {

  private final CreateOrganization createOrganization;

  public OrganizationService(CreateOrganization createOrganization) {
    this.createOrganization = createOrganization;
  }

  @Transactional
  public CreateOrganizationOutput create(CreateOrganizationInput input) {
    return this.createOrganization.create(input);
  }
}
