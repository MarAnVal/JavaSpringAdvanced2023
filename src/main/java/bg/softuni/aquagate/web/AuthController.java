package bg.softuni.aquagate.web;

import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/identity")
public interface AuthController {

    @ModelAttribute("userRegistrationDTO")
    UserRegistrationDTO initRegistrationForm();

    @GetMapping("/register")
    String register();

    @PostMapping("/register")
    String doRegister(@Valid UserRegistrationDTO userRegistrationDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) ;

    @GetMapping("/login")
    String login();

    //TODO logout?
}
