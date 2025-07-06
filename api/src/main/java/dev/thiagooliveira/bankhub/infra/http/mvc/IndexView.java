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
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexView {

  private final AccountService accountService;
  private final CategoryService categoryService;

  public IndexView(AccountService accountService, CategoryService categoryService) {
    this.accountService = accountService;
    this.categoryService = categoryService;
  }

  @GetMapping({"/", "/index", "/index.html", "/{month:\\d{4}-\\d{2}}"})
  public ModelAndView index(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @PathVariable(name = "month", required = false) YearMonth month) {

    YearMonth selectedMonth = (month != null) ? month : YearMonth.now();

    AppModelAndView model = new AppModelAndView("index", userPrincipal);
    model.updateYearMonth(selectedMonth);

    if (!YearMonth.now().equals(selectedMonth)) {
      model.addObject("balanceDescription", "forecast for the end of the month");
    }

    var accounts = accountService.findByOrganizationId(userPrincipal.getOrganizationId());
    var account = accounts.get(0); // TODO

    model.addObject("accounts", accounts);
    model.addObject("account", account);

    addCategoriesToModel(userPrincipal, model);
    addFormInputsToModel(model);
    addMonthlySummaryToModel(model, account.id(), selectedMonth);

    return model;
  }

  private void addCategoriesToModel(UserPrincipal userPrincipal, AppModelAndView model) {
    List<Category> categories = categoryService.findAll(userPrincipal.getOrganizationId());
    addFilteredCategories(model, "credCategories", categories, Category::isCredit);
    addFilteredCategories(model, "debitCategories", categories, Category::isDebit);
  }

  private void addFilteredCategories(
      AppModelAndView model,
      String attributeName,
      List<Category> categories,
      Predicate<Category> filter) {
    var filtered = categories.stream().filter(filter).toList();
    if (!filtered.isEmpty()) {
      model.addObject(attributeName, filtered);
    }
  }

  private void addFormInputsToModel(AppModelAndView model) {
    model.addObject("withdrawalInput", new CreateTransactionInput("withdrawal"));
    model.addObject("depositInput", new CreateTransactionInput("deposit"));
    model.addObject("receivableInput", new CreatePayableReceivableInput());
    model.addObject("payableInput", new CreatePayableReceivableInput());
  }

  private void addMonthlySummaryToModel(AppModelAndView model, UUID accountId, YearMonth month) {
    var summary =
        accountService
            .getMonthlyAccountSummary(accountId, month)
            .orElse(
                new MonthlyAccountSummary(
                    accountId,
                    month,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO));
    model.addObject("accountSummary", summary);
  }
}
