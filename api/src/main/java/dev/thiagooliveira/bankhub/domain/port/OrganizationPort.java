package dev.thiagooliveira.bankhub.domain.port;

import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.bankhub.domain.model.Organization;
import java.util.List;
import java.util.UUID;

public interface OrganizationPort {
  Organization create(CreateOrganizationInput input);

  List<Organization> list(UUID organizationId);
}
