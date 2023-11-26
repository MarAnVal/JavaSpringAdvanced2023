package bg.softuni.aquagateclient.web.controller;

import bg.softuni.aquagateclient.data.model.CommentAddDTO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
@RequestMapping("/topics/details/{id}/comments")
public interface CommentsController {

    @ModelAttribute("commentAddDTO")
    CommentAddDTO initAddCommentForm();

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    String doCommentAdd(@PathVariable Long id, @Valid CommentAddDTO commentAddDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Principal principal);
}
