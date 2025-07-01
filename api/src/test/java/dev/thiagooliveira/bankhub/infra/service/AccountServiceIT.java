package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.IntegrationTest;
import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Pageable;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AccountServiceIT extends IntegrationTest {

  @Autowired private AccountService accountService;

  @Autowired private OrganizationService organizationService;

  @Autowired private BankService bankService;

  @Autowired private TransactionService transactionService;

  private UUID organizationId;

  private UUID bankId;

  @BeforeEach
  void setUp() {
    this.organizationId = this.organizationService.create(createOrganizationInput()).id();
    this.bankId = this.bankService.create(createBankInput(this.organizationId)).id();
  }

  @Test
  void getAccounts() {
    var account =
        this.accountService.create(
            createAccountInput(this.organizationId, this.bankId, Currency.EUR), BigDecimal.TEN);

    var enriched =
        this.accountService
            .findByIdAndOrganizationIdEnriched(account.id(), account.organizationId())
            .orElseThrow();
    assertNotNull(enriched);
    assertEquals(account.id(), enriched.id());
    assertEquals(account.name(), enriched.name());
    assertEquals(0, account.balance().compareTo(enriched.balance()));
    assertEquals(account.currency(), enriched.currency());
    assertEquals(account.bankId(), enriched.bank().id());
    assertNotNull(enriched.bank().name());
  }

  @Test
  void create() {
    var account =
        this.accountService.create(
            createAccountInput(this.organizationId, this.bankId, Currency.EUR), BigDecimal.TEN);
    assertNotNull(account);
    assertEquals(this.organizationId, account.organizationId());
    assertEquals(this.bankId, account.bankId());
    assertEquals(Currency.EUR, account.currency());
    assertEquals(BigDecimal.TEN, account.balance());

    var pageTransactions =
        this.transactionService.findEnrichedByFiltersOrderByDateTime(
            new GetTransactionPageable(
                List.of(account.id()), this.organizationId, Pageable.of(0, 10)));
    assertNotNull(pageTransactions);
    assertFalse(pageTransactions.content().isEmpty());
  }
}
