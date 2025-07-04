package dev.thiagooliveira.bankhub.infra.http.mvc;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginView {

  @GetMapping({"/login", "login.html"})
  public String login(HttpServletRequest request) {
    if (request.getUserPrincipal() != null) {
      return "redirect:/";
    }
    return "login";
  }
}
