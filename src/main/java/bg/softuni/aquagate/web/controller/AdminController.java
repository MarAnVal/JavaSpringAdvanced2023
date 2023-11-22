package bg.softuni.aquagate.web.controller;

import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.web.interceptor.annotation.PageTitle;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("admin")
public interface AdminController {

    @GetMapping("/pending")
    @PageTitle("Pending")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    ModelAndView pending();

    @GetMapping("/pending/details/{id}")
    @PageTitle("Pending details")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    ModelAndView pendingDetails(@PathVariable Long id);

    @PostMapping("pending/remove/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    ModelAndView remove(@PathVariable Long id);

    @PostMapping("pending/approve/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    ModelAndView approve(@PathVariable Long id);

    @ModelAttribute("userEditDTO")
    UserEditDTO initEditUserForm();

    @GetMapping("/users-edit")
    @PageTitle("Edit user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ModelAndView editUser();

    @PostMapping("/users-edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    String doEditUser(@Valid UserEditDTO userEditDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes);
}
