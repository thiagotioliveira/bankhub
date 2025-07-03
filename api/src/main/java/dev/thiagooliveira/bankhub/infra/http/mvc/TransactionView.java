package dev.thiagooliveira.bankhub.infra.http.mvc;

import dev.thiagooliveira.bankhub.domain.model.Transaction;
import dev.thiagooliveira.bankhub.infra.config.support.AppProperties;
import dev.thiagooliveira.bankhub.infra.http.mvc.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.infra.service.AccountService;
import dev.thiagooliveira.bankhub.infra.service.TransactionService;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransactionView {

  private final AppProperties appProps;
  private final AccountService accountService;
  private final TransactionService transactionService;

  public TransactionView(
      AppProperties appProps,
      AccountService accountService,
      TransactionService transactionService) {
    this.appProps = appProps;
    this.accountService = accountService;
    this.transactionService = transactionService;
  }

  @PostMapping("/withdrawal-verification")
  public ModelAndView withdrawalVerification(@ModelAttribute CreateTransactionInput input) {
    var account =
        this.accountService
            .findByIdAndOrganizationIdEnriched(input.getAccountId(), appProps.getOrganizationId())
            .orElseThrow();
    input.setType("withdrawal");
    ModelAndView modelAndView = new ModelAndView("transaction-verification");
    modelAndView.addObject("account", account);
    modelAndView.addObject("amount", input.getAmount());
    modelAndView.addObject("input", input);
    return modelAndView;
  }

  @PostMapping("/deposit-verification")
  public ModelAndView depositVerification(@ModelAttribute CreateTransactionInput input) {
    var account =
        this.accountService
            .findByIdAndOrganizationIdEnriched(input.getAccountId(), appProps.getOrganizationId())
            .orElseThrow();
    input.setType("deposit");
    ModelAndView modelAndView = new ModelAndView("transaction-verification");
    modelAndView.addObject("account", account);
    modelAndView.addObject("amount", input.getAmount());
    modelAndView.addObject("input", input);
    return modelAndView;
  }

  @PostMapping("/transaction-detail")
  public ModelAndView transactionSubmit(@ModelAttribute CreateTransactionInput input) {
    Transaction transaction = null;
    if ("deposit".equals(input.getType())) {
      transaction =
          this.transactionService.createDeposit(
              new dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput(
                  input.getAccountId(),
                  this.appProps.getOrganizationId(),
                  OffsetDateTime.now(),
                  Optional.ofNullable(input.getDescription()).orElse("deposit"),
                  input.getCategoryId(),
                  input.getAmount()));
    } else {
      transaction =
          this.transactionService.createWithdrawal(
              new dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput(
                  input.getAccountId(),
                  this.appProps.getOrganizationId(),
                  OffsetDateTime.now(),
                  Optional.ofNullable(input.getDescription()).orElse("withdrawal"),
                  input.getCategoryId(),
                  input.getAmount()));
    }
    ModelAndView modelAndView = new ModelAndView("transaction-detail");
    modelAndView.addObject("transaction", transaction);
    return modelAndView;
  }
}
