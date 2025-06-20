package dev.thiagooliveira.bankhub.infra.service;

import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.TestcontainersConfiguration;
import dev.thiagooliveira.bankhub.domain.dto.OrganizationRegistrationInput;
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
  void register() {
    var output =
        this.organizationService.register(
            new OrganizationRegistrationInput("Jack Bauer", "j.bauer@bankhub.app"));
    assertNotNull(output);
    assertNotNull(output.id());
    assertNotNull(output.createdAt());
    assertNotNull(output.userOwner());
  }
}
