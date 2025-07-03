package dev.thiagooliveira.bankhub.infra.config.support;

import dev.thiagooliveira.bankhub.application.usecase.CreateUser;
import dev.thiagooliveira.bankhub.domain.dto.*;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.domain.model.Currency;
import dev.thiagooliveira.bankhub.infra.persistence.repository.OrganizationRepository;
import dev.thiagooliveira.bankhub.infra.service.AccountService;
import dev.thiagooliveira.bankhub.infra.service.BankService;
import dev.thiagooliveira.bankhub.infra.service.CategoryService;
import dev.thiagooliveira.bankhub.infra.service.OrganizationService;
import java.util.HashSet;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("local")
public class DatabaseSeeder implements CommandLineRunner {

  private final AppSeedProperties appSeedProperties;
  private final AppProperties appProps;
  private final OrganizationService organizationService;
  private final OrganizationRepository organizationRepository;
  private final CreateUser createUser;
  private final BankService bankService;
  private final AccountService accountService;
  private final CategoryService categoryService;

  public DatabaseSeeder(
      AppSeedProperties appSeedProperties,
      AppProperties appProps,
      OrganizationService organizationService,
      OrganizationRepository organizationRepository,
      CreateUser createUser,
      BankService bankService,
      AccountService accountService,
      CategoryService categoryService) {
    this.appSeedProperties = appSeedProperties;
    this.appProps = appProps;
    this.organizationService = organizationService;
    this.organizationRepository = organizationRepository;
    this.createUser = createUser;
    this.bankService = bankService;
    this.accountService = accountService;
    this.categoryService = categoryService;
  }

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    if (!appSeedProperties.isEnabled()) return;

    if (organizationRepository.count() == 0) {
      var userOwner = appSeedProperties.getUsers().stream().findFirst().orElseThrow();
      var organization =
          this.organizationService.create(
              new CreateOrganizationInput(userOwner.getName(), userOwner.getEmail()));
      this.appProps.setOrganizationId(organization.id());

      var otherUsers = new HashSet<>(appSeedProperties.getUsers());
      otherUsers.remove(userOwner);
      otherUsers.forEach(
          u ->
              this.createUser.create(
                  new CreateUserInput(u.getName(), u.getEmail(), organization.id())));
      appSeedProperties
          .getBanks()
          .forEach(
              b -> {
                var bank =
                    this.bankService.create(new CreateBankInput(organization.id(), b.getName()));
                b.getAccounts()
                    .forEach(
                        a -> {
                          this.accountService.create(
                              new CreateAccountInput(
                                  a.getName(),
                                  bank.id(),
                                  organization.id(),
                                  Currency.valueOf(a.getCurrency())),
                              a.getBalance());
                        });
              });

      appSeedProperties
          .getCategories()
          .forEach(
              c -> {
                this.categoryService.create(
                    new CreateCategoryInput(
                        Optional.of(organization.id()),
                        c.getName(),
                        CategoryType.valueOf(c.getType())));
              });

      System.out.println("Organization Created: " + this.appProps.getOrganizationId());
    } else {
      var organization = this.organizationRepository.findAll().get(0);
      this.appProps.setOrganizationId(organization.getId());
      System.out.println("Organization Id: " + this.appProps.getOrganizationId());
    }
  }
}
