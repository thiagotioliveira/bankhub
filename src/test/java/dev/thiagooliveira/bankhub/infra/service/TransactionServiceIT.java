package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.IntegrationTest;
import dev.thiagooliveira.bankhub.domain.dto.GetTransactionPageable;
import dev.thiagooliveira.bankhub.domain.dto.Pageable;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TransactionServiceIT extends IntegrationTest {

  @Autowired private TransactionService transactionService;

  @Autowired private OrganizationService organizationService;

  @Autowired private AccountService accountService;

  @Autowired private BankService bankService;

  private UUID organizationId;
  private UUID accountId;

  @BeforeEach
  void setUp() {
    this.organizationId = this.organizationService.create(createOrganizationInput()).id();
    var bankId = this.bankService.create(createBankInput(organizationId)).id();
    this.accountId =
        this.accountService
            .create(createAccountInput(organizationId, bankId, Currency.EUR), BigDecimal.TEN)
            .id();
  }

  @Test
  void create() {
    var transaction =
        this.transactionService.create(
            createTransactionInput(
                this.accountId, this.organizationId, OffsetDateTime.now(), BigDecimal.TEN));
    assertNotNull(transaction);
    assertNotNull(transaction.id());
    assertNotNull(transaction.dateTime());
    assertNotNull(transaction.description());
    assertEquals(this.accountId, transaction.accountId());
    assertEquals(BigDecimal.TEN, transaction.amount());
  }

  @Test
  void getTransactionsByAccountId() {
    var transaction1 =
        this.transactionService.create(
            createTransactionInput(
                this.accountId, this.organizationId, OffsetDateTime.now(), BigDecimal.TEN));
    var transaction2 =
        this.transactionService.create(
            createTransactionInput(
                this.accountId, this.organizationId, OffsetDateTime.now(), BigDecimal.ONE));
    var page =
        this.transactionService.findByAccountId(
            new GetTransactionPageable(this.accountId, Pageable.of(0, 10)));
    assertNotNull(page);
    assertEquals(3, page.totalElements());
    assertEquals(1, page.totalPages());
    assertTrue(page.first());
    assertTrue(page.last());

    assertEquals(transaction2.id(), page.content().get(0).id());
    assertEquals(transaction1.id(), page.content().get(1).id());
  }

  @Test
  void throwExceptionWhenAmountIsZero() {
    assertThrows(
        BusinessLogicException.class,
        () ->
            this.transactionService.create(
                createTransactionInput(
                    this.accountId, this.organizationId, OffsetDateTime.now(), BigDecimal.ZERO)));
  }
}
