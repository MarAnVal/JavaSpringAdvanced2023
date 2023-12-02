package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.service.CommentService;
import bg.softuni.aquagateclient.web.controller.CommentsController;
import bg.softuni.aquagateclient.web.error.CommentNotFoundException;
import bg.softuni.aquagateclient.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
                                     RedirectAttributes redirectAttributes, Principal principal) {

        ModelAndView modelAndView = new ModelAndView();

        commentAddDTO.setAuthor(principal.getName());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentAddDTO", commentAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.commentAddDTO",
                            bindingResult);

        } else {

            try {

                commentService.addComment(commentAddDTO);
            } catch (UserNotFoundException | CommentNotFoundException e) {
                //TODO ExceptionHandler
                throw new RuntimeException(e);
            }
        }
        modelAndView.setViewName("redirect:/topics/details/" + commentAddDTO.getTopicId());
        return modelAndView;
    }
}
