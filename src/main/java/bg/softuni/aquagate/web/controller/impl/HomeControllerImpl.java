package bg.softuni.aquagate.web.controller.impl;

import bg.softuni.aquagate.data.entity.Picture;
import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.view.PictureView;
import bg.softuni.aquagate.data.view.TopicsView;
import bg.softuni.aquagate.service.PictureService;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.web.controller.HomeController;
import bg.softuni.aquagate.web.error.PictureNotFoundException;
import bg.softuni.aquagate.web.error.TopicNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
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
    public ModelAndView index(Principal principal) {
        Topic topic;
        try {
            topic = topicService.getMostCommented();
        } catch (TopicNotFoundException e) {
            topic = topicService.getNoTopicUnit();
        }

        TopicsView mostCommented = modelMapper.map(topic, TopicsView.class);
        mostCommented.setPictureUrl(topic.getPictures().get(0).getPictureUrl());


        List<PictureView> pictureViews;
        try {
            List<Picture> pictures = pictureService.getLatestPictures();
            pictureViews = pictures
                    .stream()
                    .map(e -> modelMapper.map(e, PictureView.class))
                    .collect(Collectors.toList());
        } catch (PictureNotFoundException e) {
            pictureViews = pictureService.getNoGalleryPicturesUnit().stream()
                    .map(p -> modelMapper.map(p, PictureView.class))
                    .collect(Collectors.toList());
        }

        ModelAndView model = new ModelAndView("index");
        model.addObject("lastPictures", pictureViews);
        model.addObject("mostCommented", mostCommented);
        return model;
    }

    @Override
    public ModelAndView about() {
        return new ModelAndView("about");
    }

}
