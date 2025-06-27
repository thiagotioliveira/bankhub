package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.createOrganizationInput;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OrganizationServiceIT extends IntegrationTest {

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
