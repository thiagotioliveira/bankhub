package dev.thiagooliveira.bankhub.infra.http.mvc;

import dev.thiagooliveira.bankhub.domain.model.Transaction;
import dev.thiagooliveira.bankhub.infra.http.mvc.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.infra.security.UserPrincipal;
import dev.thiagooliveira.bankhub.infra.service.AccountService;
import dev.thiagooliveira.bankhub.infra.service.TransactionService;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransactionView {

  private final AccountService accountService;
  private final TransactionService transactionService;

  public TransactionView(AccountService accountService, TransactionService transactionService) {
    this.accountService = accountService;
    this.transactionService = transactionService;
  }

  @PostMapping("/withdrawal-verification")
  public ModelAndView withdrawalVerification(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @ModelAttribute CreateTransactionInput input) {
    var account =
        this.accountService
            .findByIdAndOrganizationIdEnriched(
                input.getAccountId(), userPrincipal.getOrganizationId())
            .orElseThrow();
    input.setType("withdrawal");
    ModelAndView modelAndView = new ModelAndView("transaction-verification");
    modelAndView.addObject("account", account);
    modelAndView.addObject("amount", input.getAmount());
    modelAndView.addObject("input", input);
    return modelAndView;
  }

  @PostMapping("/deposit-verification")
  public ModelAndView depositVerification(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @ModelAttribute CreateTransactionInput input) {
    var account =
        this.accountService
            .findByIdAndOrganizationIdEnriched(
                input.getAccountId(), userPrincipal.getOrganizationId())
            .orElseThrow();
    input.setType("deposit");
    ModelAndView modelAndView = new ModelAndView("transaction-verification");
    modelAndView.addObject("account", account);
    modelAndView.addObject("amount", input.getAmount());
    modelAndView.addObject("input", input);
    return modelAndView;
  }

  @PostMapping("/transaction-detail")
  public ModelAndView transactionSubmit(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @ModelAttribute CreateTransactionInput input) {
    Transaction transaction = null;
    if ("deposit".equals(input.getType())) {
      transaction =
          this.transactionService.createDeposit(
              new dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput(
                  input.getAccountId(),
                  userPrincipal.getOrganizationId(),
                  OffsetDateTime.now(),
                  Optional.ofNullable(input.getDescription()).orElse("deposit"),
                  input.getCategoryId(),
                  input.getAmount()));
    } else {
      transaction =
          this.transactionService.createWithdrawal(
              new dev.thiagooliveira.bankhub.domain.dto.CreateTransactionInput(
                  input.getAccountId(),
                  userPrincipal.getOrganizationId(),
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
