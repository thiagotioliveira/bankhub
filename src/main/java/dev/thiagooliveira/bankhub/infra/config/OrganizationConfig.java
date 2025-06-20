package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.CreateOrganization;
import dev.thiagooliveira.bankhub.domain.port.OrganizationPort;
import dev.thiagooliveira.bankhub.domain.port.UserPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationConfig {

  @Bean
  public CreateOrganization createOrganization(
      OrganizationPort organizationPort, UserPort userPort) {
    return new CreateOrganization(organizationPort, userPort);
  }
}
