package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.data.view.TopicView;
import bg.softuni.aquagate.data.view.TopicsAllView;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.web.TopicController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TopicControllerImpl implements TopicController {
    private final TopicService topicService;
    private final ModelMapper modelMapper;


    @Autowired
    public TopicControllerImpl(TopicService topicService, ModelMapper modelMapper) {
        this.topicService = topicService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String topics(Model model) {
        //TODO filter all approved topics
        List<TopicsAllView> allApprovedTopics = topicService.getAllApprovedTopics()
                .stream()
                .map(e -> {
                    return modelMapper.map(e, TopicsAllView.class);
                })
                .collect(Collectors.toList());

        model.addAttribute(allApprovedTopics);

        return "topics";
    }

    //TODO add approved habitat selected topics pages (/topics/freshwater and so on)
    // or leave the habitat pages only like information ones with back home button?

    @Override
    public String addTopic() {
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

        this.topicService.Add(topicAddDTO);

        return "redirect:/";
    }

    @Override
    public TopicAddDTO initAddForm(){
        return new TopicAddDTO();
    }

    @Override
    public String topicDetails() {
        //TODO add parameter and model and change path to topics/details/{id}
        return "topic-details";
    }

    @Override
    public String latestTopic(Model model) {
        TopicView topicView = modelMapper.map(topicService.findLatestTopic(), TopicView.class);
        return "redirect:/topics/details/" + topicView.getId();
    }

    //TODO refactor in adminController?

    @Override
    public String approve(Model model) {
        //TODO moderator and administrator could approve
        List<TopicsAllView> allApprovedTopics = topicService.getAllNotApprovedTopics()
                .stream()
                .map(e -> {
                    return modelMapper.map(e, TopicsAllView.class);
                })
                .collect(Collectors.toList());

        //TODO check for no topics
        model.addAttribute(allApprovedTopics);

        return "topics";
    }

    //TODO postMapping for topics/approve/details/{id} and topics/remove/details/{id}
    // think about better paths

    @Override
    public String remove() {
        //TODO add parameter and postMapping
        // admin could remove not approved topics
        return null;
    }
}
