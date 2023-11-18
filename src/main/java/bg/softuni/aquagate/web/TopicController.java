package bg.softuni.aquagate.web;

import bg.softuni.aquagate.data.model.CommentAddDTO;
import bg.softuni.aquagate.data.model.PictureAddDTO;
import bg.softuni.aquagate.data.model.TopicAddDTO;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@RequestMapping("/topics")
public interface TopicController {

    @GetMapping("/")
    String topics(Model model);

    @ModelAttribute("topicAddDTO")
    TopicAddDTO initAddTopicForm();

    @GetMapping("/add")
    String addTopic();

    @PostMapping("/add")
    String doAddTopic(@Valid TopicAddDTO topicAddDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Principal principal) throws IOException;

    @GetMapping("/details/{id}")
    String topicDetails(@PathVariable Long id, Model model);

    @GetMapping("/latest")
    String latestTopic();

    @GetMapping("/most-popular")
    String mostCommented();

    @PostMapping("/comments/add/{id}")
    String doCommentAdd(@PathVariable Long id, @Valid CommentAddDTO commentAddDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Principal principal);

    @PostMapping("/comments/add/{id}")
    String doPictureAdd(@PathVariable Long id, @Valid PictureAddDTO commentAddDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Principal principal);
}
