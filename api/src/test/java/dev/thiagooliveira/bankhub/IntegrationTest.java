package dev.thiagooliveira.bankhub;

import dev.thiagooliveira.bankhub.infra.persistence.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class IntegrationTest {

  @Autowired private OrganizationRepository organizationRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private AccountRepository accountRepository;
  @Autowired private MonthlyAccountSummaryRepository monthlyAccountSummaryRepository;
  @Autowired private TransactionRepository transactionRepository;
  @Autowired private BankRepository bankRepository;
  @Autowired private CategoryRepository categoryRepository;
  @Autowired private PayableReceivableRepository payableReceivableRepository;
  @Autowired private PaymentRepository paymentRepository;
  @Autowired private PayableReceivableTemplateRepository templateRepository;

  @AfterEach
  void tearDown() {
    this.paymentRepository.deleteAll();
    this.payableReceivableRepository.deleteAll();
    this.templateRepository.deleteAll();
    this.transactionRepository.deleteAll();
    this.monthlyAccountSummaryRepository.deleteAll();
    this.accountRepository.deleteAll();
    this.bankRepository.deleteAll();
    this.userRepository.deleteAll();
    this.categoryRepository.deleteAllWhereOrganizationIdIsNotNull();
    this.organizationRepository.deleteAll();
  }
}
