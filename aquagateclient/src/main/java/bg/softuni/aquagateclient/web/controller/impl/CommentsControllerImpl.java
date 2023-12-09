package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.service.CommentService;
import bg.softuni.aquagateclient.web.controller.CommentsController;
import bg.softuni.aquagateclient.web.error.BadRequestException;
import bg.softuni.aquagateclient.web.error.BaseApplicationException;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class CommentsControllerImpl implements CommentsController {

    private final CommentService commentService;

    @Autowired
    public CommentsControllerImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public CommentAddDTO initAddCommentForm() {
        return new CommentAddDTO();
    }

    @Override
    public ModelAndView doCommentAdd(CommentAddDTO commentAddDTO, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Principal principal,
                                     Long id) throws BadRequestException, ObjectNotFoundException {

        ModelAndView modelAndView = new ModelAndView();
        commentAddDTO.setTopicId(id);
        commentAddDTO.setAuthor(principal.getName());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentAddDTO", commentAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.commentAddDTO",
                            bindingResult);

        } else {
            commentService.addComment(commentAddDTO);
        }
        modelAndView.setViewName("redirect:/topics/details/" + id);
        return modelAndView;
    }

    @ExceptionHandler({ObjectNotFoundException.class, BadRequestException.class})
    public ModelAndView handleApplicationExceptions(BaseApplicationException e) {
        ModelAndView modelAndView = new ModelAndView("error-page");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }

}
