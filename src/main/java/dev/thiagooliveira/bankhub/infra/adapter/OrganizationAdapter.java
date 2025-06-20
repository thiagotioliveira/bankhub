package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.bankhub.domain.model.Organization;
import dev.thiagooliveira.bankhub.domain.port.OrganizationPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.OrganizationEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.OrganizationRepository;
import org.springframework.stereotype.Component;

@Component
public class OrganizationAdapter implements OrganizationPort {

  private final OrganizationRepository organizationRepository;

  public OrganizationAdapter(OrganizationRepository organizationRepository) {
    this.organizationRepository = organizationRepository;
  }

  @Override
  public Organization create(CreateOrganizationInput input) {
    return this.organizationRepository.save(OrganizationEntity.from(input)).toDomain();
  }
}
