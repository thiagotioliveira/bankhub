package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.CreateOrganization;
import dev.thiagooliveira.bankhub.application.usecase.CreateUser;
import dev.thiagooliveira.bankhub.application.usecase.GetOrganization;
import dev.thiagooliveira.bankhub.domain.port.OrganizationPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationConfig {

  @Bean
  public CreateOrganization createOrganization(
      OrganizationPort organizationPort, CreateUser createUser) {
    return new CreateOrganization(organizationPort, createUser);
  }

  @Bean
  public GetOrganization getOrganization(OrganizationPort organizationPort) {
    return new GetOrganization(organizationPort);
  }
}
