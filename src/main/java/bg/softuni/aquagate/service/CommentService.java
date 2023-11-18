package bg.softuni.aquagate.service;

import bg.softuni.aquagate.data.entity.Comment;
import bg.softuni.aquagate.data.view.CommentView;
import bg.softuni.aquagate.repository.CommentRepo;
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

    public void remove(Comment comment) {
        //TODO check for null or wrong value
        commentRepo.delete(comment);
    }

    public List<CommentView> mapCommentViewList(List<Comment> comments) {
        //TODO check for null value
        return comments.stream()
                .map(e -> modelMapper.map(e, CommentView.class))
                .collect(Collectors.toList());
    }
}
