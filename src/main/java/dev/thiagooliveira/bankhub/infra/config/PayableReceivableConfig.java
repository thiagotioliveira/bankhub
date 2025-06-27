package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.CreateReceivable;
import dev.thiagooliveira.bankhub.domain.port.PayableReceivablePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayableReceivableConfig {

  @Bean
  public CreateReceivable createReceivable(PayableReceivablePort port) {
    return new CreateReceivable(port);
  }
}
