package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.TestcontainersConfiguration;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class AccountServiceIT {

  @Autowired private AccountService accountService;

  @Autowired private OrganizationService organizationService;

  @Autowired private BankService bankService;

  private UUID organizationId;

  private UUID bankId;

  @BeforeEach
  void setUp() {
    this.organizationId = this.organizationService.create(createOrganizationInput()).id();
    this.bankId = this.bankService.create(createBankInput(this.organizationId)).id();
  }

  @Test
  void create() {
    var account =
        this.accountService.create(
            createAccountInput(this.organizationId, this.bankId, BigDecimal.TEN, Currency.EUR));
    assertNotNull(account);
    assertEquals(this.organizationId, account.organizationId());
    assertEquals(this.bankId, account.bankId());
    assertEquals(Currency.EUR, account.currency());
    assertEquals(BigDecimal.TEN, account.balance());
  }
}
