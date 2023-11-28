package bg.softuni.aquagatedb.web.controller.impl;

import bg.softuni.aquagatedb.data.model.CommentAddDTO;
import bg.softuni.aquagatedb.data.view.CommentView;
import bg.softuni.aquagatedb.service.CommentService;
import bg.softuni.aquagatedb.web.controller.CommentsController;
import bg.softuni.aquagatedb.web.error.CommentNotFoundException;
import bg.softuni.aquagatedb.web.error.TopicNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentsControllerImpl implements CommentsController {

    private final CommentService commentService;

    public CommentsControllerImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public ResponseEntity<CommentView> doCommentAdd(@RequestBody CommentAddDTO commentAddDTO,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            try {
                return ResponseEntity.ok(commentService.addComment(commentAddDTO));

            } catch (CommentNotFoundException | TopicNotFoundException e) {
                return ResponseEntity.badRequest().build();
            }
        }
    }
}
