package dev.thiagooliveira.bankhub.infra.config.support;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
  private UUID organizationId;

  public UUID getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(UUID organizationId) {
    this.organizationId = organizationId;
  }
}
