package bg.softuni.aquagate.web.impl;

import bg.softuni.aquagate.data.view.PictureView;
import bg.softuni.aquagate.data.view.TopicsView;
import bg.softuni.aquagate.service.PictureService;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.web.HomeController;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeControllerImpl implements HomeController {
    private final TopicService topicService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    public HomeControllerImpl(TopicService topicService, PictureService pictureService, ModelMapper modelMapper) {
        this.topicService = topicService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String index(Model model) {
        TopicsView topicsView = modelMapper.map(topicService.getMostCommented(), TopicsView.class);
        List<PictureView> pictureViews = pictureService.getLatestPictures()
                .stream()
                .map(e -> modelMapper.map(e, PictureView.class))
                .collect(Collectors.toList());

        model.addAttribute("lastPictures", pictureViews);
        model.addAttribute("mostCommented", topicsView);
        return "index";
    }

    @Override
    public String about() {
        return "about";
    }
}
