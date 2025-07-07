package dev.thiagooliveira.bankhub.infra.http.mvc;

import dev.thiagooliveira.bankhub.domain.dto.projection.RevenueExpenseByCategory;
import dev.thiagooliveira.bankhub.domain.model.CategoryType;
import dev.thiagooliveira.bankhub.infra.http.mvc.dto.AppModelAndView;
import dev.thiagooliveira.bankhub.infra.security.UserPrincipal;
import dev.thiagooliveira.bankhub.infra.service.AccountService;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportView {

  private final AccountService accountService;

  public ReportView(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping({"/report", "/report/{month:\\d{4}-\\d{2}}"})
  public ModelAndView report(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @PathVariable(name = "month", required = false) YearMonth month) {
    YearMonth selectedMonth = (month != null) ? month : YearMonth.now();
    AppModelAndView modelAndView = new AppModelAndView("report", userPrincipal);
    modelAndView.updateBottomMenuActive("report");
    modelAndView.updateYearMonth(selectedMonth);

    var accounts = accountService.findByOrganizationId(userPrincipal.getOrganizationId());
    var account = accounts.get(0); // TODO
    List<RevenueExpenseByCategory> list =
        accountService.getRevenueExpenseByCategory(account.id(), selectedMonth);
    var revenues = list.stream().filter(i -> i.type().equals(CategoryType.CREDIT.name())).toList();
    var expenses = list.stream().filter(i -> i.type().equals(CategoryType.DEBIT.name())).toList();

    List<String> revenueLabels =
        revenues.stream().map(RevenueExpenseByCategory::categoryName).toList();

    List<BigDecimal> revenueSeries =
        revenues.stream().map(RevenueExpenseByCategory::amount).toList();

    List<String> expenseLabels =
        expenses.stream().map(RevenueExpenseByCategory::categoryName).toList();

    List<BigDecimal> expenseSeries =
        expenses.stream().map(RevenueExpenseByCategory::amount).toList();

    modelAndView.addObject("revenueLabels", revenueLabels);
    modelAndView.addObject("revenueSeries", revenueSeries);
    modelAndView.addObject("expenseLabels", expenseLabels);
    modelAndView.addObject("expenseSeries", expenseSeries);
    return modelAndView;
  }
}
