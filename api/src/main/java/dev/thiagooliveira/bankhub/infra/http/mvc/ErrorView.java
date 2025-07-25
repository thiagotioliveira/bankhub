package dev.thiagooliveira.bankhub.infra.http.mvc;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorView implements ErrorController {
  @RequestMapping("/error")
  public String handleError() {
    return "error";
  }
}
