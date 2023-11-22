package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Comment;
import bg.softuni.aquagate.data.view.CommentView;
import bg.softuni.aquagate.repository.CommentRepo;
import bg.softuni.aquagate.web.error.CommentNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(CommentRepo commentRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
    }

    public void remove(Comment comment) throws CommentNotFoundException {
        commentRepo.findById(comment.getId()).orElseThrow(CommentNotFoundException::new);
        commentRepo.delete(comment);
    }

    public List<CommentView> mapCommentViewList(List<Comment> comments) {
        return comments.stream()
                .map(e -> modelMapper.map(e, CommentView.class))
                .collect(Collectors.toList());
    }
}
