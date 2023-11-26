package bg.softuni.aquagatedb.web.controller.impl;

import bg.softuni.aquagatedb.data.model.CommentAddDTO;
import bg.softuni.aquagatedb.data.view.CommentView;
import bg.softuni.aquagatedb.service.CommentService;
import bg.softuni.aquagatedb.web.controller.CommentsController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentsControllerImpl implements CommentsController {
    private final CommentService commentService;

    public CommentsControllerImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public ResponseEntity<CommentView> doCommentAdd(CommentAddDTO commentAddDTO, BindingResult bindingResult) {
        //TODO validate commentAddDTO
        commentService.addComment(commentAddDTO);
        return null;
    }
}
