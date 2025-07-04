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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = false)
public class SeedConfig {

  @Bean
  @Transactional
  public CommandLineRunner init(
      AppSeedProperties appSeedProperties,
      AppProperties appProps,
      OrganizationService organizationService,
      OrganizationRepository organizationRepository,
      CreateUser createUser,
      BankService bankService,
      AccountService accountService,
      CategoryService categoryService) {
    return (args) -> {
      if (organizationRepository.count() == 0) {
        var userOwner = appSeedProperties.getUsers().stream().findFirst().orElseThrow();
        var organization =
            organizationService.create(
                new CreateOrganizationInput(
                    userOwner.getName(), userOwner.getEmail(), userOwner.getPassword()));

        var otherUsers = new HashSet<>(appSeedProperties.getUsers());
        otherUsers.remove(userOwner);
        otherUsers.forEach(
            u ->
                createUser.create(
                    new CreateUserInput(
                        u.getName(), u.getEmail(), u.getPassword(), organization.id())));
        appSeedProperties
            .getBanks()
            .forEach(
                b -> {
                  var bank =
                      bankService.create(new CreateBankInput(organization.id(), b.getName()));
                  b.getAccounts()
                      .forEach(
                          a -> {
                            accountService.create(
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
                  categoryService.create(
                      new CreateCategoryInput(
                          Optional.of(organization.id()),
                          c.getName(),
                          CategoryType.valueOf(c.getType())));
                });

        System.out.println("Organization Created: " + organization.id());
      } else {
        var organization = organizationRepository.findAll().get(0);
        System.out.println("Organization Id: " + organization.getId());
      }
    };
  }
}
