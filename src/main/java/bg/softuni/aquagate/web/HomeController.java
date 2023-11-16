package bg.softuni.aquagate.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface HomeController {
    @GetMapping("/")
    String index(Model model);
}
