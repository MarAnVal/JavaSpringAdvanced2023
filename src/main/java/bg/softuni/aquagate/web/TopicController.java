package bg.softuni.aquagate.web;

import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.data.model.UserRegistrationDTO;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/topics")
public interface TopicController {

    @GetMapping("/")
    public String topics();

    @GetMapping("/add")
    public String addTopic();

    @PostMapping("/add")
    public String doAddTopic(@Valid TopicAddDTO topicAddDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) ;

    @ModelAttribute("topicAddDTO")
    public UserEditDTO initAddForm();

    @GetMapping("/details")
    public String topicDetails();

    @GetMapping("/freshwater")
    public String freshwater();

    @GetMapping("/reef")
    public String reef();

    @GetMapping("/blackwater")
    public String blackwater();

    @GetMapping("/brackish-water")
    public String brackishWater();

    @PostMapping("/approve")
    public String approve();

    @PostMapping("/remove")
    public String remove();
}
