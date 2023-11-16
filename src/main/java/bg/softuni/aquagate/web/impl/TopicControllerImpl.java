package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.data.model.UserEditDTO;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.web.TopicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TopicControllerImpl implements TopicController {
    private final TopicService topicService;

    @Autowired
    public TopicControllerImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public String topics() {
        //TODO
        return "topics";
    }

    @Override
    public String addTopic() {
        //TODO
        return "add-topic";
    }

    @Override
    public String doAddTopic(TopicAddDTO topicAddDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("topicAddDTO", topicAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.topicAddDTO",
                            bindingResult);

            return "redirect:topics/add";
        }

        this.topicService.register(topicAddDTO);

        return "redirect:/";
    }

    @Override
    public UserEditDTO initAddForm(){
        return new UserEditDTO();
    }

    @Override
    public String topicDetails() {
        //TODO
        return "topic-details";
    }

    @Override
    public String freshwater() {

        return "freshwater";
    }

    @Override
    public String reef() {

        return "reef";
    }

    @Override
    public String blackwater() {

        return "blackwater";
    }

    @Override
    public String brackishWater() {

        return "brackish-water";
    }

    @Override
    public String approve() {
        //TODO
        return null;
    }

    @Override
    public String remove() {
        //TODO
        return null;
    }
}
