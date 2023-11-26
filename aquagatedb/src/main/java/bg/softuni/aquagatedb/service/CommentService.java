package bg.softuni.aquagatedb.service;

import bg.softuni.aquagatedb.data.entity.Comment;
import bg.softuni.aquagatedb.data.model.CommentAddDTO;
import bg.softuni.aquagatedb.repository.CommentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(CommentRepo commentRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
    }

    public void addComment(CommentAddDTO commentAddDTO) {

        Comment comment = modelMapper.map(commentAddDTO, Comment.class);

        commentRepo.save(comment);
    }

    public void removeByTopicId(Long id) {
        List<Comment> commentsByTopicId = commentRepo.findCommentsByTopicId(id);
        commentRepo.deleteAll(commentsByTopicId);
    }
}
