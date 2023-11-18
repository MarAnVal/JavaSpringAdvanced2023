package bg.softuni.aquagate.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
@RequestMapping("users")
public interface UsersController {

    @GetMapping("/profile")
    String profile(Principal principal, Model model);
}
