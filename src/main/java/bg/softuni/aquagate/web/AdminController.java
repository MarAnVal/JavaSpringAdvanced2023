package bg.softuni.aquagate.web;

import bg.softuni.aquagate.data.model.UserEditDTO;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@RequestMapping("admin")
public interface AdminController {

    @GetMapping("/pending")
    String pending(Model model);
    @GetMapping("/pending/details/{id}")
    String pendingDetails(@PathVariable Long id, Model model);

    @PostMapping("pending/remove/{id}")
    String remove(@PathVariable Long id);
    @PostMapping("pending/approve/{id}")
    String approve(@PathVariable Long id);

    @ModelAttribute("userEditDTO")
    UserEditDTO initEditUserForm();

    @GetMapping("/users-edit")
    String editUser();

    @PostMapping("/users-edit")
    String doEditUser(@Valid UserEditDTO userEditDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) ;
}
