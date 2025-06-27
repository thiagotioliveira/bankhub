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
  @Autowired private TransactionRepository transactionRepository;
  @Autowired private BankRepository bankRepository;
  @Autowired private CategoryRepository categoryRepository;
  @Autowired private PayableReceivableRepository payableReceivableRepository;

  @AfterEach
  void tearDown() {
    this.payableReceivableRepository.deleteAll();
    this.transactionRepository.deleteAll();
    this.accountRepository.deleteAll();
    this.bankRepository.deleteAll();
    this.userRepository.deleteAll();
    this.categoryRepository.deleteAllWhereOrganizationIdIsNotNull();
    this.organizationRepository.deleteAll();
  }
}
