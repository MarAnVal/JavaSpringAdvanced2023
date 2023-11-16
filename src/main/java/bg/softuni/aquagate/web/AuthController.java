package bg.softuni.aquagate.web;

import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@RequestMapping("/users")
public interface AuthController {

    @ModelAttribute("userRegistrationDTO")
    public UserRegistrationDTO initRegistrationForm();

    @GetMapping("/register")
    public String register();

    @PostMapping("/register")
    public String doRegister(@Valid UserRegistrationDTO userRegistrationDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) ;

    @GetMapping("/login")
    public String login();

    @GetMapping("/profile")
    public String profile(Principal principal, Model model);

    @ModelAttribute("userEditDTO")
    public UserEditDTO initEditForm();

    @GetMapping("/edit")
    public String editUser();

    @PostMapping("/edit")
    public String doEditUser(@Valid UserEditDTO userEditDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) ;
}
