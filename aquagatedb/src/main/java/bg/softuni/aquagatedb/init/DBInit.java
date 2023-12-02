package bg.softuni.aquagatedb.init;

import bg.softuni.aquagatedb.service.CommentService;
import bg.softuni.aquagatedb.service.HabitatService;
import bg.softuni.aquagatedb.service.TopicService;
import bg.softuni.aquagatedb.web.error.ObjectNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final HabitatService habitatService;
    private final TopicService topicService;
    private final CommentService commentService;

    public DBInit(HabitatService habitatService, TopicService topicService, CommentService commentService) {
        this.habitatService = habitatService;
        this.topicService = topicService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) {
        habitatService.init();
        try {
            topicService.initTestData();
            commentService.initTestData();
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
