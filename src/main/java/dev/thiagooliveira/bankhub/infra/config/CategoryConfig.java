package dev.thiagooliveira.bankhub.infra.config;

import dev.thiagooliveira.bankhub.application.usecase.CreateCategory;
import dev.thiagooliveira.bankhub.application.usecase.GetCategory;
import dev.thiagooliveira.bankhub.domain.port.CategoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfig {

  @Bean
  public CreateCategory createCategory(CategoryPort categoryPort) {
    return new CreateCategory(categoryPort);
  }

  @Bean
  public GetCategory getCategory(CategoryPort categoryPort) {
    return new GetCategory(categoryPort);
  }
}
