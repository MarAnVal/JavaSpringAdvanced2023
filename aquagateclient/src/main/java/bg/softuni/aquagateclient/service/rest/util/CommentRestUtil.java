package bg.softuni.aquagateclient.service.rest.util;

import bg.softuni.aquagateclient.model.dto.binding.CommentAddDTO;
import bg.softuni.aquagateclient.model.dto.request.CommentRequestAddDTO;
import org.springframework.http.HttpEntity;

public record CommentRestUtil(String commentAddUrl) {

    public HttpEntity<CommentRequestAddDTO> getHttpAddComment(CommentAddDTO commentAddDTO, Long userId) {
        CommentRequestAddDTO commentRequestAddDTO = mapCommentRequestAddDTO(commentAddDTO, userId);
        return new HttpEntity<>(commentRequestAddDTO);
    }

    private CommentRequestAddDTO mapCommentRequestAddDTO(CommentAddDTO commentAddDTO, Long userId) {
        CommentRequestAddDTO commentRequestAddDTO = new CommentRequestAddDTO();
        commentRequestAddDTO.setContext(commentAddDTO.getContext());
        commentRequestAddDTO.setTopicId(commentAddDTO.getTopicId());
        commentRequestAddDTO.setAuthorId(userId);

        return commentRequestAddDTO;
    }
}
