package bg.softuni.aquagate.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/topics")
public interface TopicController {

    @GetMapping("/")
    public String topics(Model model);

    @GetMapping("/add")
    public String addTopic();

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
