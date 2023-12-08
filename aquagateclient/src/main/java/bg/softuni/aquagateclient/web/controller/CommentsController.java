package bg.softuni.aquagateclient.web.controller;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
@RequestMapping("/topics/details/{id}/comments")
public interface CommentsController {

    @ModelAttribute("commentAddDTO")
    CommentAddDTO initAddCommentForm();

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    ModelAndView doCommentAdd(@Valid CommentAddDTO commentAddDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Principal principal, @PathVariable Long id) throws BadRequestException, ObjectNotFoundException;
}
