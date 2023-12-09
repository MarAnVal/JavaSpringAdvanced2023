package bg.softuni.aquagatedb.web.controller;

import bg.softuni.aquagatedb.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagatedb.model.dto.view.CommentView;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/comments")
@CrossOrigin("*")
public interface CommentsController {

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    ResponseEntity<CommentView> doCommentAdd(@Valid @RequestBody CommentAddDTO commentAddDTO, BindingResult bindingResult) throws ObjectNotFoundException;
}
