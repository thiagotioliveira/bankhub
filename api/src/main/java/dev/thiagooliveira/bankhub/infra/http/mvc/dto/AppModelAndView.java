package dev.thiagooliveira.bankhub.infra.http.mvc.dto;

import dev.thiagooliveira.bankhub.infra.security.UserPrincipal;
import java.time.YearMonth;
import org.springframework.web.servlet.ModelAndView;

public class AppModelAndView extends ModelAndView {

  private static final String YEAR_MONTH = "selectedMonth";
  private static final String USER_AUTH = "userAuth";

  public AppModelAndView(String viewName, UserPrincipal userPrincipal) {
    super(viewName);
    this.getModelMap().addAttribute(YEAR_MONTH, YearMonth.now());
    this.getModelMap().addAttribute(USER_AUTH, userPrincipal.getName());
  }

  public void updateYearMonth(YearMonth yearMonth) {
    this.getModelMap().addAttribute(YEAR_MONTH, yearMonth);
  }
}
