package bg.softuni.aquagatedb.web.controller.impl;

import bg.softuni.aquagatedb.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagatedb.model.dto.view.CommentView;
import bg.softuni.aquagatedb.service.CommentService;
import bg.softuni.aquagatedb.web.controller.CommentsController;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
                                                    BindingResult bindingResult) throws ObjectNotFoundException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
                return ResponseEntity.ok(commentService.addComment(commentAddDTO));
        }
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<CommentView> handleApplicationExceptions() {

        return ResponseEntity.notFound().build();
    }
}
