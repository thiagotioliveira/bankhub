package dev.thiagooliveira.bankhub.infra.http.mvc.dto;

import java.time.YearMonth;
import org.springframework.web.servlet.ModelAndView;

public class AppModelAndView extends ModelAndView {

  private static final String YEAR_MONTH = "selectedMonth";

  public AppModelAndView(String viewName) {
    super(viewName);
    this.getModelMap().addAttribute(YEAR_MONTH, YearMonth.now());
  }

  public void updateYearMonth(YearMonth yearMonth) {
    this.getModelMap().addAttribute(YEAR_MONTH, yearMonth);
  }
}
