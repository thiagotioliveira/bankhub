package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.createOrganizationInput;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.TestcontainersConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class OrganizationServiceIT {

  @Autowired private OrganizationService organizationService;

  @BeforeEach
  void setUp() {}

  @Test
  void create() {
    var output = this.organizationService.create(createOrganizationInput());
    assertNotNull(output);
    assertNotNull(output.id());
    assertNotNull(output.createdAt());
    assertNotNull(output.userOwner());
  }
}
