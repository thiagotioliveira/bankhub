package dev.thiagooliveira.bankhub.infra.http.mvc;

import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import dev.thiagooliveira.bankhub.infra.config.AppProps;
import dev.thiagooliveira.bankhub.infra.http.mvc.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.infra.service.AccountService;
import dev.thiagooliveira.bankhub.infra.service.CategoryService;
import dev.thiagooliveira.bankhub.infra.service.PayableReceivableService;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PayableReceivableView {

  private final AppProps appProps;
  private final AccountService accountService;
  private final CategoryService categoryService;
  private final PayableReceivableService payableReceivableService;

  public PayableReceivableView(
      AppProps appProps,
      AccountService accountService,
      CategoryService categoryService,
      PayableReceivableService payableReceivableService) {
    this.appProps = appProps;
    this.accountService = accountService;
    this.categoryService = categoryService;
    this.payableReceivableService = payableReceivableService;
  }

  @PostMapping("/payable-verification")
  public ModelAndView payablePayable(@ModelAttribute CreatePayableReceivableInput input) {
    input.setType("payable");
    var account =
        this.accountService
            .findByIdAndOrganizationIdEnriched(
                input.getAccountId(), this.appProps.getOrganizationId())
            .orElseThrow();
    var category =
        this.categoryService
            .findById(input.getCategoryId(), this.appProps.getOrganizationId())
            .orElseThrow();
    ModelAndView modelAndView = new ModelAndView("payable-receivable-verification");
    modelAndView.addObject("icon", "alert-outline");
    modelAndView.addObject("type", "Payable");
    modelAndView.addObject("input", input);
    modelAndView.addObject("account", account);
    modelAndView.addObject("category", category);
    return modelAndView;
  }

  @PostMapping("/receivable-verification")
  public ModelAndView payableReceivable(@ModelAttribute CreatePayableReceivableInput input) {
    input.setType("Receivable");
    var account =
        this.accountService
            .findByIdAndOrganizationIdEnriched(
                input.getAccountId(), this.appProps.getOrganizationId())
            .orElseThrow();
    var category =
        this.categoryService
            .findById(input.getCategoryId(), this.appProps.getOrganizationId())
            .orElseThrow();
    ModelAndView modelAndView = new ModelAndView("payable-receivable-verification");
    modelAndView.addObject("type", "Receivable");
    modelAndView.addObject("icon", "wallet-outline");
    modelAndView.addObject("input", input);
    modelAndView.addObject("account", account);
    modelAndView.addObject("category", category);
    return modelAndView;
  }

  @PostMapping("/payable-receivable-detail")
  public ModelAndView payableReceivableSubmit(@ModelAttribute CreatePayableReceivableInput input) {
    var account =
        this.accountService
            .findByIdAndOrganizationIdEnriched(
                input.getAccountId(), this.appProps.getOrganizationId())
            .orElseThrow();
    var category =
        this.categoryService
            .findById(input.getCategoryId(), this.appProps.getOrganizationId())
            .orElseThrow();

    ModelAndView modelAndView = new ModelAndView("payable-receivable-detail");
    if ("Receivable".equals(input.getType())) {
      var pr =
          this.payableReceivableService.create(
              new dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput(
                  PayableReceivableType.RECEIVABLE,
                  this.appProps.getOrganizationId(),
                  input.getAccountId(),
                  input.getCategoryId(),
                  input.getDescription(),
                  input.getAmount(),
                  LocalDate.now(),
                  input.isRecurring(),
                  Optional.ofNullable(Frequency.valueOf(input.getFrequence())),
                  Optional.ofNullable(input.getInstallmentTotal())));
      modelAndView.addObject("type", "Receivable");
      modelAndView.addObject("saved", pr);
    } else {
      var pr =
          this.payableReceivableService.create(
              new dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput(
                  PayableReceivableType.PAYABLE,
                  this.appProps.getOrganizationId(),
                  input.getAccountId(),
                  input.getCategoryId(),
                  input.getDescription(),
                  input.getAmount(),
                  LocalDate.now(),
                  input.isRecurring(),
                  Optional.ofNullable(Frequency.valueOf(input.getFrequence())),
                  Optional.ofNullable(input.getInstallmentTotal())));
      modelAndView.addObject("type", "Payable");
      modelAndView.addObject("saved", pr);
    }
    modelAndView.addObject("account", account);
    modelAndView.addObject("category", category);
    return modelAndView;
  }
}
