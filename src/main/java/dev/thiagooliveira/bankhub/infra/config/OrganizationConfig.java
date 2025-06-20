package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.OrganizationRegistration;
import dev.thiagooliveira.bankhub.domain.port.OrganizationPort;
import dev.thiagooliveira.bankhub.domain.port.UserPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationConfig {

  @Bean
  public OrganizationRegistration organizationRegistration(
      OrganizationPort organizationPort, UserPort userPort) {
    return new OrganizationRegistration(organizationPort, userPort);
  }
}
