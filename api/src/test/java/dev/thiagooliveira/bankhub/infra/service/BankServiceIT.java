package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.createBankInput;
import static dev.thiagooliveira.bankhub.util.TestUtil.createOrganizationInput;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.IntegrationTest;
import dev.thiagooliveira.bankhub.domain.model.Bank;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BankServiceIT extends IntegrationTest {

  @Autowired private BankService bankService;

  @Autowired private OrganizationService organizationService;

  private UUID organizationId;

  @BeforeEach
  void setUp() {
    this.organizationId = this.organizationService.create(createOrganizationInput()).id();
  }

  @Test
  void create() {
    Bank bank = this.bankService.create(createBankInput(this.organizationId));
    assertNotNull(bank);
    assertNotNull(bank.name());
    assertEquals(organizationId, bank.organizationId());
  }
}
