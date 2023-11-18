package bg.softuni.aquagate.web;

import bg.softuni.aquagate.data.model.TopicAddDTO;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@RequestMapping("/topics")
public interface TopicController {

    @GetMapping("/all")
    public String topics(Model model);

    @GetMapping("/add")
    public String addTopic();

    @PostMapping("/add")
    public String doAddTopic(@Valid TopicAddDTO topicAddDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal) throws IOException;

    @ModelAttribute("topicAddDTO")
    public TopicAddDTO initAddTopicForm();

    //TODO change the path to /details/{id},
    // add parameter for id and model,
    // do postMapping

    @GetMapping("/details")
    public String topicDetails();

    @GetMapping("/latest")
    public String latestTopic(Model model);

    //TODO refactor in adminController?

    @PostMapping("/approve")
    public String approve(Model model);

    @PostMapping("/remove")
    public String remove();
}
