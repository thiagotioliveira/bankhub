package dev.thiagooliveira.bankhub.infra.config.support;

import dev.thiagooliveira.bankhub.domain.dto.CreateOrganizationInput;
import dev.thiagooliveira.bankhub.infra.config.AppProps;
import dev.thiagooliveira.bankhub.infra.persistence.repository.OrganizationRepository;
import dev.thiagooliveira.bankhub.infra.service.OrganizationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDataCommandLineRunner implements CommandLineRunner {

  private final AppProps appProps;
  private final OrganizationService organizationService;
  private final OrganizationRepository organizationRepository;

  public InitDataCommandLineRunner(
      AppProps appProps,
      OrganizationService organizationService,
      OrganizationRepository organizationRepository) {
    this.appProps = appProps;
    this.organizationService = organizationService;
    this.organizationRepository = organizationRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    var organizations = this.organizationRepository.findAll();
    if (organizations.isEmpty()) {
      var organization =
          this.organizationService.create(
              new CreateOrganizationInput("Thiago Oliveira", "thiago@thiagoti.com"));
      this.appProps.setOrganizationId(organization.id());
      System.out.println("Organization Created: " + organization.id());
    } else {
      this.appProps.setOrganizationId(organizations.get(0).getId());
      System.out.println("Organization Id: " + organizations.get(0).getId());
    }
  }
}
