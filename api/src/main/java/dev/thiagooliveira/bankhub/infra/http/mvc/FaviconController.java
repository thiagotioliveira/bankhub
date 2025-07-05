package dev.thiagooliveira.bankhub.infra.http.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaviconController {
  @GetMapping("favicon.ico")
  public String favicon() {
    return "redirect:/assets/img/favicon.png";
  }
}
