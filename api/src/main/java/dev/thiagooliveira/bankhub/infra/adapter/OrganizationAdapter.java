package dev.thiagooliveira.bankhub.infra.adapter;

import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.bankhub.domain.model.Organization;
import dev.thiagooliveira.bankhub.domain.port.OrganizationPort;
import dev.thiagooliveira.bankhub.infra.persistence.entity.OrganizationEntity;
import dev.thiagooliveira.bankhub.infra.persistence.repository.OrganizationRepository;
import java.util.List;
import java.util.UUID;
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

  @Override
  public List<Organization> list(UUID organizationId) {
    var organization =
        this.organizationRepository.findById(organizationId).map(OrganizationEntity::toDomain);
    return organization.map(List::of).orElseGet(List::of);
  }
}
