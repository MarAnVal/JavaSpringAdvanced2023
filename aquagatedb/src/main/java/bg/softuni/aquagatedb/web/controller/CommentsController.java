package bg.softuni.aquagatedb.web.controller;

import bg.softuni.aquagatedb.data.model.CommentAddDTO;
import bg.softuni.aquagatedb.data.view.CommentView;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/comments")
@CrossOrigin("*") //???
public interface CommentsController {

    @PostMapping("/add")
    ResponseEntity<CommentView> doCommentAdd(@Valid @RequestBody CommentAddDTO commentAddDTO, BindingResult bindingResult);
}
