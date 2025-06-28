package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.CreateBank;
import dev.thiagooliveira.bankhub.application.usecase.GetBank;
import dev.thiagooliveira.bankhub.domain.port.BankPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfig {

  @Bean
  public CreateBank createBank(BankPort bankPort) {
    return new CreateBank(bankPort);
  }

  @Bean
  public GetBank getBank(BankPort bankPort) {
    return new GetBank(bankPort);
  }
}
