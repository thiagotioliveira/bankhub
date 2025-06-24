package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.TestcontainersConfiguration;
import dev.thiagooliveira.bankhub.domain.exception.BusinessLogicException;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TransactionServiceIT {

  @Autowired private TransactionService transactionService;

  @Autowired private OrganizationService organizationService;

  @Autowired private AccountService accountService;

  @Autowired private BankService bankService;

  private UUID accountId;

  @BeforeEach
  void setUp() {
    var organizationId = this.organizationService.create(createOrganizationInput()).id();
    var bankId = this.bankService.create(createBankInput(organizationId)).id();
    this.accountId =
        this.accountService
            .create(createAccountInput(organizationId, bankId, BigDecimal.TEN, Currency.EUR))
            .id();
  }

  @Test
  void create() {
    var transaction =
        this.transactionService.create(
            createTransactionInput(this.accountId, OffsetDateTime.now(), BigDecimal.TEN));
    assertNotNull(transaction);
    assertNotNull(transaction.id());
    assertNotNull(transaction.dateTime());
    assertNotNull(transaction.description());
    assertEquals(this.accountId, transaction.accountId());
    assertEquals(BigDecimal.TEN, transaction.amount());
  }

  @Test
  void throwExceptionWhenAmountIsZero() {
    assertThrows(
        BusinessLogicException.class,
        () ->
            this.transactionService.create(
                createTransactionInput(this.accountId, OffsetDateTime.now(), BigDecimal.ZERO)));
  }
}
