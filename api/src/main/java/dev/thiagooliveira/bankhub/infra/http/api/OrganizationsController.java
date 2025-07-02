package dev.thiagooliveira.bankhub.infra.http.api;

import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.infra.config.AppProps;
import dev.thiagooliveira.bankhub.infra.http.api.mapper.OrganizationMapper;
import dev.thiagooliveira.bankhub.infra.service.OrganizationService;
import dev.thiagooliveira.bankhub.spec.http.controllers.OrganizationsApi;
import dev.thiagooliveira.bankhub.spec.http.dto.GetOrganizationsResponseBody;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationsController implements OrganizationsApi {

  private final AppProps appProps;
  private final OrganizationService organizationService;
  private final OrganizationMapper organizationMapper;

  public OrganizationsController(
      AppProps appProps,
      OrganizationService organizationService,
      OrganizationMapper organizationMapper) {
    this.appProps = appProps;
    this.organizationService = organizationService;
    this.organizationMapper = organizationMapper;
  }

  @Override
  public ResponseEntity<GetOrganizationsResponseBody> getOrganizationById(UUID id) {
    return this.organizationService
        .get(id)
        .map(o -> ResponseEntity.ok(this.organizationMapper.toGetOrganizationsResponseBody(o)))
        .orElseThrow(() -> BusinessLogicException.notFound("organization not found"));
  }

  @Override
  public ResponseEntity<List<GetOrganizationsResponseBody>> listOrganizations() {
    return ResponseEntity.ok(
        this.organizationService.list(this.appProps.getOrganizationId()).stream()
            .map(this.organizationMapper::toGetOrganizationsResponseBody)
            .toList());
  }
}
