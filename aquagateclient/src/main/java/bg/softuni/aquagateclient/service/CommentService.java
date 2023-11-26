package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.configuration.ApplicationCommentsConfiguration;
import bg.softuni.aquagateclient.data.model.CommentAddDTO;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final ApplicationCommentsConfiguration applicationCommentsConfiguration;

    public CommentService(ApplicationCommentsConfiguration applicationCommentsConfiguration) {
        this.applicationCommentsConfiguration = applicationCommentsConfiguration;
    }

    public void addComment(CommentAddDTO commentAddDTO) {
        String url = applicationCommentsConfiguration.commentsAddUrlSource();
        //TODO
    }
}
