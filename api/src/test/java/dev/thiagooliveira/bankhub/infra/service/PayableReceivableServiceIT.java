package dev.thiagooliveira.bankhub.infra.service;

import static dev.thiagooliveira.bankhub.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.thiagooliveira.bankhub.IntegrationTest;
import dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.domain.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PayableReceivableServiceIT extends IntegrationTest {

  @Autowired private OrganizationService organizationService;

  @Autowired private AccountService accountService;

  @Autowired private BankService bankService;

  @Autowired private CategoryService categoryService;

  @Autowired private PayableReceivableService payableReceivableService;

  private UUID organizationId;
  private UUID accountId;
  private UUID creditCategoryId;
  private UUID debitCategoryId;

  @BeforeEach
  void setUp() {
    this.organizationId = this.organizationService.create(createOrganizationInput()).id();
    var bankId = this.bankService.create(createBankInput(organizationId)).id();
    this.creditCategoryId = this.categoryService.findByType(CategoryType.CREDIT).get().id();
    this.debitCategoryId = this.categoryService.findByType(CategoryType.DEBIT).get().id();
    this.accountId =
        this.accountService
            .create(createAccountInput(organizationId, bankId, Currency.EUR), BigDecimal.ZERO)
            .id();
  }

  /*
    @Test
    void pay() {
      var payable =
          this.payableReceivableService.create(
              new CreatePayableReceivableInput(
                  PayableReceivableType.PAYABLE,
                  this.organizationId,
                  this.accountId,
                  this.debitCategoryId,
                  "Rent Apartment",
                  new BigDecimal("100"),
                  LocalDate.now().plusDays(5),
                  false,
                  Optional.empty(),
                  Optional.empty()));

      assertNotNull(payable);

      var payablePaid =
          this.payableReceivableService.pay(
              new CreatePaymentInput(
                  payable.id(),
                  this.organizationId,
                  Optional.empty(),
                  Optional.empty(),
                  Optional.empty()));

      assertNotNull(payablePaid);
      assertEquals(PayableReceivableStatus.PAID, payablePaid.status());
      assertTrue(payablePaid.paymentId().isPresent());
    }
  */
  @Test
  void createPayableRecurringByMonth() {
    LocalDate dueDate = LocalDate.now().plusDays(5);
    var receivable =
        this.payableReceivableService.create(
            new CreatePayableReceivableInput(
                PayableReceivableType.PAYABLE,
                this.organizationId,
                this.accountId,
                this.debitCategoryId,
                "Rent Apartment",
                new BigDecimal("100"),
                dueDate,
                true,
                Optional.of(Frequency.MONTHLY),
                Optional.empty()));

    assertNotNull(receivable);

    assertNotNull(receivable);
    assertNotNull(receivable.id());
    assertEquals(this.debitCategoryId, receivable.categoryId());
    assertEquals(this.accountId, receivable.accountId());
    assertNotNull(receivable.description());
    assertEquals(new BigDecimal("100"), receivable.amount());
    assertEquals(dueDate, receivable.dueDate());
    assertEquals(PayableReceivableStatus.PENDING, receivable.status());
    assertFalse(receivable.installmentNumber().isPresent());
    assertFalse(receivable.installmentTotal().isPresent());
    assertFalse(receivable.paymentId().isPresent());
  }

  @Test
  void createPayableInstallments() {
    LocalDate dueDate = LocalDate.now().plusDays(5);
    var receivable =
        this.payableReceivableService.create(
            new CreatePayableReceivableInput(
                PayableReceivableType.PAYABLE,
                this.organizationId,
                this.accountId,
                this.debitCategoryId,
                "Payment of any purchase in installments",
                new BigDecimal("100"),
                dueDate,
                false,
                Optional.of(Frequency.MONTHLY),
                Optional.of(10)));

    assertNotNull(receivable);

    assertNotNull(receivable);
    assertNotNull(receivable.id());
    assertEquals(this.debitCategoryId, receivable.categoryId());
    assertEquals(this.accountId, receivable.accountId());
    assertNotNull(receivable.description());
    assertEquals(new BigDecimal("10.00"), receivable.amount());
    assertEquals(dueDate, receivable.dueDate());
    assertEquals(PayableReceivableStatus.PENDING, receivable.status());
    assertTrue(receivable.installmentNumber().isPresent());
    assertEquals(10, receivable.installmentTotal().get());
    assertFalse(receivable.paymentId().isPresent());
    dueDate = dueDate.plusMonths(1);
  }

  @Test
  void createReceivableRecurringByMonth() {
    LocalDate dueDate = LocalDate.now().plusDays(5);
    var receivable =
        this.payableReceivableService.create(
            new CreatePayableReceivableInput(
                PayableReceivableType.RECEIVABLE,
                this.organizationId,
                this.accountId,
                this.creditCategoryId,
                "Salary",
                new BigDecimal("1000"),
                dueDate,
                true,
                Optional.of(Frequency.MONTHLY),
                Optional.empty()));

    assertNotNull(receivable);

    assertNotNull(receivable);
    assertNotNull(receivable.id());
    assertEquals(this.creditCategoryId, receivable.categoryId());
    assertEquals(this.accountId, receivable.accountId());
    assertNotNull(receivable.description());
    assertEquals(new BigDecimal("1000"), receivable.amount());
    assertEquals(dueDate, receivable.dueDate());
    assertEquals(PayableReceivableStatus.PENDING, receivable.status());
    assertFalse(receivable.installmentNumber().isPresent());
    assertFalse(receivable.installmentTotal().isPresent());
    assertFalse(receivable.paymentId().isPresent());
  }

  @Test
  void createReceivableInstallments() {
    LocalDate dueDate = LocalDate.now().plusDays(5);
    var receivable =
        this.payableReceivableService.create(
            new CreatePayableReceivableInput(
                PayableReceivableType.RECEIVABLE,
                this.organizationId,
                this.accountId,
                this.creditCategoryId,
                "Payment of any receivable in installments",
                new BigDecimal("1000"),
                dueDate,
                false,
                Optional.of(Frequency.MONTHLY),
                Optional.of(10)));

    assertNotNull(receivable);

    assertNotNull(receivable);
    assertNotNull(receivable.id());
    assertEquals(this.creditCategoryId, receivable.categoryId());
    assertEquals(this.accountId, receivable.accountId());
    assertNotNull(receivable.description());
    assertEquals(new BigDecimal("100.00"), receivable.amount());
    assertEquals(dueDate, receivable.dueDate());
    assertEquals(PayableReceivableStatus.PENDING, receivable.status());
    assertTrue(receivable.installmentNumber().isPresent());
    assertEquals(10, receivable.installmentTotal().get());
    assertFalse(receivable.paymentId().isPresent());
    dueDate = dueDate.plusMonths(1);
  }
}
