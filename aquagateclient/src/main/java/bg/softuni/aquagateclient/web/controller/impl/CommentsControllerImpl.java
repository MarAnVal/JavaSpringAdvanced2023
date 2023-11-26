package bg.softuni.aquagateclient.web.controller.impl;

import bg.softuni.aquagateclient.data.model.CommentAddDTO;
import bg.softuni.aquagateclient.service.CommentService;
import bg.softuni.aquagateclient.web.controller.CommentsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    public String doCommentAdd(Long id, CommentAddDTO commentAddDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentAddDTO", commentAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.commentAddDTO",
                            bindingResult);

            return "redirect:allApprovedTopics/" + id;
        }

        commentAddDTO.setAuthorUsername(principal.getName());
        commentAddDTO.setTopicId(id);
        //TODO ExceptionHandler
        commentService.addComment(commentAddDTO);

        return "redirect:allApprovedTopics/" + id;
    }
}
