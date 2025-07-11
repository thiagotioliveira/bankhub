package dev.thiagooliveira.bankhub.infra.http.api.mapper;

import dev.thiagooliveira.bankhub.domain.model.Organization;
import dev.thiagooliveira.bankhub.spec.http.dto.GetOrganizationsResponseBody;
import org.mapstruct.Mapper;

@Mapper
public interface OrganizationMapper {
  GetOrganizationsResponseBody toGetOrganizationsResponseBody(Organization organization);
}
