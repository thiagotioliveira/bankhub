package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.CreateUser;
import dev.thiagooliveira.bankhub.domain.port.UserPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

  @Bean
  public CreateUser createUser(UserPort userPort) {
    return new CreateUser(userPort);
  }
}
