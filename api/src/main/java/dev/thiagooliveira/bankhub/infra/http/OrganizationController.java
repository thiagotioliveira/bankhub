package dev.thiagooliveira.bankhub.infra.http;

import dev.thiagooliveira.bankhub.http.controllers.OrganizationsApi;
import dev.thiagooliveira.bankhub.http.dto.GetOrganizationsResponseBody;
import dev.thiagooliveira.bankhub.infra.mapper.OrganizationMapper;
import dev.thiagooliveira.bankhub.infra.service.OrganizationService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationController implements OrganizationsApi {

  private final OrganizationService organizationService;
  private final OrganizationMapper organizationMapper;

  public OrganizationController(
      OrganizationService organizationService, OrganizationMapper organizationMapper) {
    this.organizationService = organizationService;
    this.organizationMapper = organizationMapper;
  }

  @Override
  public ResponseEntity<List<GetOrganizationsResponseBody>> listOrganizations() {
    return ResponseEntity.ok(
        this.organizationService.list(UUID.randomUUID()).stream()
            .map(this.organizationMapper::toGetOrganizationsResponseBody)
            .toList());
  }
}
