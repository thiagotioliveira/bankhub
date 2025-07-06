package dev.thiagooliveira.bankhub.infra.http.mvc;

import dev.thiagooliveira.bankhub.domain.dto.CreatePaymentInput;
import dev.thiagooliveira.bankhub.domain.model.Frequency;
import dev.thiagooliveira.bankhub.domain.model.PayableReceivableType;
import dev.thiagooliveira.bankhub.infra.http.mvc.dto.AppModelAndView;
import dev.thiagooliveira.bankhub.infra.http.mvc.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.infra.http.mvc.dto.UpdatePayableReceivableInput;
import dev.thiagooliveira.bankhub.infra.security.UserPrincipal;
import dev.thiagooliveira.bankhub.infra.service.AccountService;
import dev.thiagooliveira.bankhub.infra.service.CategoryService;
import dev.thiagooliveira.bankhub.infra.service.PayableReceivableService;
import dev.thiagooliveira.bankhub.infra.service.PaymentService;
import java.time.YearMonth;
import java.util.Optional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PayableReceivableView {

  private final AccountService accountService;
  private final CategoryService categoryService;
  private final PayableReceivableService payableReceivableService;
  private final PaymentService paymentService;

  public PayableReceivableView(
      AccountService accountService,
      CategoryService categoryService,
      PayableReceivableService payableReceivableService,
      PaymentService paymentService) {
    this.accountService = accountService;
    this.categoryService = categoryService;
    this.payableReceivableService = payableReceivableService;
    this.paymentService = paymentService;
  }

  @GetMapping({"/payable-receivable-list", "/payable-receivable-list/{month:\\d{4}-\\d{2}}"})
  public ModelAndView list(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @RequestParam(defaultValue = "payables") String tab,
      @PathVariable(name = "month", required = false) YearMonth month) {
    YearMonth selectedMonth = (month != null) ? month : YearMonth.now();
    var from = selectedMonth.atDay(1);
    var to = selectedMonth.atEndOfMonth();

    var accounts = this.accountService.findByOrganizationId(userPrincipal.getOrganizationId());
    var account = accounts.get(0);

    var items = payableReceivableService.getByAccountIdOrderByDueDateAsc(account.id(), from, to);
    var payables = items.stream().filter(i -> i.type() == PayableReceivableType.PAYABLE).toList();
    var receivables =
        items.stream().filter(i -> i.type() == PayableReceivableType.RECEIVABLE).toList();

    AppModelAndView modelAndView = new AppModelAndView("payable-receivable-list", userPrincipal);
    modelAndView.updateYearMonth(selectedMonth);
    modelAndView.addObject("tab", tab);
    modelAndView.addObject("payables", payables);
    modelAndView.addObject("receivables", receivables);
    return modelAndView;
  }

  @PostMapping("/payable-verification")
  public ModelAndView payablePayable(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @ModelAttribute CreatePayableReceivableInput input) {
    input.setType("payable");
    var account =
        this.accountService
            .findByIdAndOrganizationIdEnriched(
                input.getAccountId(), userPrincipal.getOrganizationId())
            .orElseThrow();
    var category =
        this.categoryService
            .findById(input.getCategoryId(), userPrincipal.getOrganizationId())
            .orElseThrow();
    ModelAndView modelAndView = new ModelAndView("payable-receivable-verification");
    modelAndView.addObject("icon", "alert-outline");
    modelAndView.addObject("type", "Payable");
    modelAndView.addObject("input", input);
    modelAndView.addObject("account", account);
    modelAndView.addObject("category", category);
    return modelAndView;
  }

  @PostMapping("/payable-receivable-edit/{month:\\d{4}-\\d{2}}")
  public String updatePayableReceivable(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @RequestParam(defaultValue = "payables") String tab,
      @PathVariable(name = "month", required = false) YearMonth month,
      @ModelAttribute UpdatePayableReceivableInput input) {
    if (input.isPaid()) {
      this.paymentService.create(
          new CreatePaymentInput(
              input.getId(),
              userPrincipal.getOrganizationId(),
              Optional.empty(),
              Optional.empty()));
    } else {
      this.payableReceivableService.update(
          new dev.thiagooliveira.bankhub.domain.dto.UpdatePayableReceivableInput(
              input.getId(),
              userPrincipal.getOrganizationId(),
              Optional.of(input.getAmount()),
              Optional.of(input.getDueDate())));
    }
    return "redirect:/payable-receivable-list/" + month.toString() + "?tab=" + tab;
  }

  @PostMapping("/receivable-verification")
  public ModelAndView payableReceivable(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @ModelAttribute CreatePayableReceivableInput input) {
    input.setType("Receivable");
    var account =
        this.accountService
            .findByIdAndOrganizationIdEnriched(
                input.getAccountId(), userPrincipal.getOrganizationId())
            .orElseThrow();
    var category =
        this.categoryService
            .findById(input.getCategoryId(), userPrincipal.getOrganizationId())
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
  public ModelAndView payableReceivableSubmit(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @ModelAttribute CreatePayableReceivableInput input) {
    var account =
        this.accountService
            .findByIdAndOrganizationIdEnriched(
                input.getAccountId(), userPrincipal.getOrganizationId())
            .orElseThrow();
    var category =
        this.categoryService
            .findById(input.getCategoryId(), userPrincipal.getOrganizationId())
            .orElseThrow();

    ModelAndView modelAndView = new ModelAndView("payable-receivable-detail");
    if ("Receivable".equals(input.getType())) {
      var pr =
          this.payableReceivableService.create(
              new dev.thiagooliveira.bankhub.domain.dto.CreatePayableReceivableInput(
                  PayableReceivableType.RECEIVABLE,
                  userPrincipal.getOrganizationId(),
                  input.getAccountId(),
                  input.getCategoryId(),
                  input.getDescription(),
                  input.getAmount(),
                  input.getStartDate(),
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
                  userPrincipal.getOrganizationId(),
                  input.getAccountId(),
                  input.getCategoryId(),
                  input.getDescription(),
                  input.getAmount(),
                  input.getStartDate(),
                  input.isRecurring(),
                  Optional.ofNullable(
                      Strings.isBlank(input.getFrequence())
                          ? null
                          : Frequency.valueOf(input.getFrequence())),
                  Optional.ofNullable(input.getInstallmentTotal())));
      modelAndView.addObject("type", "Payable");
      modelAndView.addObject("saved", pr);
    }
    modelAndView.addObject("account", account);
    modelAndView.addObject("category", category);
    return modelAndView;
  }
}
