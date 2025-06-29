package dev.thiagooliveira.bankhub.infra.service;

import dev.thiagooliveira.bankhub.application.usecase.CreateOrganization;
import dev.thiagooliveira.bankhub.application.usecase.GetOrganization;
import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationOutput;
import dev.thiagooliveira.bankhub.domain.model.Organization;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationService {

  private final CreateOrganization createOrganization;
  private final GetOrganization getOrganization;

  public OrganizationService(
      CreateOrganization createOrganization, GetOrganization getOrganization) {
    this.createOrganization = createOrganization;
    this.getOrganization = getOrganization;
  }

  @Transactional
  public CreateOrganizationOutput create(CreateOrganizationInput input) {
    return this.createOrganization.create(input);
  }

  public List<Organization> list(UUID organizationId) {
    return this.getOrganization.list(organizationId);
  }
}
