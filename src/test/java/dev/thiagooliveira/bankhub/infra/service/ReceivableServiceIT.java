package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.TestcontainersConfiguration;
import dev.thiagooliveira.bankhub.domain.dto.CreateReceivableInput;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableStatus;
import dev.thiagooliveira.bankhub.infra.persistence.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ReceivableServiceIT {

  @Autowired private OrganizationService organizationService;

  @Autowired private AccountService accountService;

  @Autowired private BankService bankService;

  @Autowired private CategoryService categoryService;

  @Autowired private ReceivableService receivableService;

  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private AccountRepository accountRepository;
  @Autowired private BankRepository bankRepository;
  @Autowired private PayableReceivableRepository payableReceivableRepository;

  private UUID accountId;
  private UUID categoryId;

  @BeforeEach
  void setUp() {
    var organizationId = this.organizationService.create(createOrganizationInput()).id();
    var bankId = this.bankService.create(createBankInput(organizationId)).id();
    this.categoryId = this.categoryService.findByType(CategoryType.CREDIT).get().id();
    this.accountId =
        this.accountService
            .create(createAccountInput(organizationId, bankId, BigDecimal.ZERO, Currency.EUR))
            .id();
  }

  @AfterEach
  void tearDown() {
    this.payableReceivableRepository.deleteAll();
    this.accountRepository.deleteAll();
    this.bankRepository.deleteAll();
    this.userRepository.deleteAll();
    this.organizationRepository.deleteAll();
  }

  @Test
  void createReceivableRecurringByMonth() {
    LocalDate dueDate = LocalDate.now().plusDays(5);
    var receivables =
        this.receivableService.create(
            new CreateReceivableInput(
                this.accountId,
                this.categoryId,
                "Salary",
                new BigDecimal("1000"),
                dueDate,
                true,
                Optional.of(Frequency.MONTHLY),
                Optional.empty()));

    assertNotNull(receivables);
    assertEquals(1, receivables.size());

    receivables.forEach(
        receivable -> {
          assertNotNull(receivable);
          assertNotNull(receivable.id());
          assertEquals(this.categoryId, receivable.categoryId());
          assertEquals(this.accountId, receivable.accountId());
          assertNotNull(receivable.description());
          assertEquals(new BigDecimal("1000"), receivable.amount());
          assertEquals(dueDate, receivable.dueDate());
          assertEquals(PayableReceivableStatus.PENDING, receivable.status());
          assertFalse(receivable.installmentNumber().isPresent());
          assertFalse(receivable.installmentTotal().isPresent());
          assertFalse(receivable.transactionId().isPresent());
        });
  }

  @Test
  void createReceivableInstallments() {
    LocalDate dueDate = LocalDate.now().plusDays(5);
    var receivables =
        this.receivableService.create(
            new CreateReceivableInput(
                this.accountId,
                this.categoryId,
                "Payment of any receivable in installments",
                new BigDecimal("1000"),
                dueDate,
                false,
                Optional.of(Frequency.MONTHLY),
                Optional.of(10)));

    assertNotNull(receivables);
    assertEquals(10, receivables.size());

    for (var receivable : receivables) {
      assertNotNull(receivable);
      assertNotNull(receivable.id());
      assertEquals(this.categoryId, receivable.categoryId());
      assertEquals(this.accountId, receivable.accountId());
      assertNotNull(receivable.description());
      assertEquals(new BigDecimal("100.00"), receivable.amount());
      assertEquals(dueDate, receivable.dueDate());
      assertEquals(PayableReceivableStatus.PENDING, receivable.status());
      assertTrue(receivable.installmentNumber().isPresent());
      assertEquals(10, receivable.installmentTotal().get());
      assertFalse(receivable.transactionId().isPresent());
      dueDate = dueDate.plusMonths(1);
    }
  }
}
