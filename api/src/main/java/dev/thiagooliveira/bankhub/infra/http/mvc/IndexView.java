package dev.thiagooliveira.bankhub.infra.http.mvc;

import dev.thiagooliveira.bankhub.domain.model.Category;
import dev.thiagooliveira.bankhub.domain.model.MonthlyAccountSummary;
import dev.thiagooliveira.bankhub.infra.http.mvc.dto.AppModelAndView;
import dev.thiagooliveira.bankhub.infra.http.mvc.dto.CreatePayableReceivableInput;
import dev.thiagooliveira.bankhub.infra.http.mvc.dto.CreateTransactionInput;
import dev.thiagooliveira.bankhub.infra.security.UserPrincipal;
import dev.thiagooliveira.bankhub.infra.service.AccountService;
import dev.thiagooliveira.bankhub.infra.service.CategoryService;
import java.math.BigDecimal;
import java.time.YearMonth;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexView {

  private final AccountService accountService;
  private final CategoryService categoryService;

  public IndexView(AccountService accountService, CategoryService categoryService) {
    this.accountService = accountService;
    this.categoryService = categoryService;
  }

  @PostMapping({"/"})
  public ModelAndView changeMonth(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @RequestParam(name = "month", required = false) YearMonth month) {
    return index(userPrincipal, month);
  }

  @GetMapping({"/"})
  public ModelAndView index(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @RequestParam(name = "month", required = false) YearMonth month) {
    YearMonth targetMonth = (month != null) ? month : YearMonth.now();

    AppModelAndView model = new AppModelAndView("index");
    model.updateYearMonth(targetMonth);

    var accounts = this.accountService.findByOrganizationId(userPrincipal.getOrganizationId());
    model.addObject("accounts", accounts);
    var account = accounts.get(0);
    model.addObject("account", account);
    var categories = this.categoryService.findAll(userPrincipal.getOrganizationId());
    var credCategories = categories.stream().filter(Category::isCredit).toList();
    if (!credCategories.isEmpty()) {
      model.addObject("credCategories", credCategories);
    }
    var debitCategories = categories.stream().filter(Category::isDebit).toList();
    if (!debitCategories.isEmpty()) {
      model.addObject("debitCategories", debitCategories);
    }
    model.addObject("withdrawalInput", new CreateTransactionInput("withdrawal"));
    model.addObject("depositInput", new CreateTransactionInput("deposit"));
    model.addObject("receivableInput", new CreatePayableReceivableInput());
    model.addObject("payableInput", new CreatePayableReceivableInput());

    var monthlyAccountSummary =
        accountService
            .getMonthlyAccountSummary(account.id(), targetMonth)
            .orElse(
                new MonthlyAccountSummary(
                    account.id(),
                    month,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO));
    model.addObject("accountSummary", monthlyAccountSummary);
    return model;
  }
}
