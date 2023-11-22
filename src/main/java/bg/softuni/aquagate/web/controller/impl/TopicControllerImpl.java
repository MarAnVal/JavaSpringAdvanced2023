package bg.softuni.aquagate.web.controller.impl;

import bg.softuni.aquagate.data.entity.Topic;
import bg.softuni.aquagate.data.model.CommentAddDTO;
import bg.softuni.aquagate.data.model.PictureAddDTO;
import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.data.view.TopicsDetailsView;
import bg.softuni.aquagate.data.view.TopicsView;
import bg.softuni.aquagate.service.TopicService;
import bg.softuni.aquagate.web.controller.TopicController;
import bg.softuni.aquagate.web.error.HabitatNotFoundException;
import bg.softuni.aquagate.web.error.PictureNotFoundException;
import bg.softuni.aquagate.web.error.TopicNotFoundException;
import bg.softuni.aquagate.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class TopicControllerImpl implements TopicController {
    private final TopicService topicService;


    @Autowired
    public TopicControllerImpl(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public ModelAndView topics() {
        List<Topic> allApprovedTopics;
        try {
            allApprovedTopics = topicService.getAllApprovedTopics();
        } catch (TopicNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        }

        List<TopicsView> allApprovedTopicsViews = topicService.mapTopicDetailsViewList(allApprovedTopics);

        ModelAndView model = new ModelAndView("topics");
        model.addObject("topics", allApprovedTopicsViews);
        return model;
    }

    @Override
    public TopicAddDTO initAddTopicForm() {
        return new TopicAddDTO();
    }

    @Override
    public ModelAndView addTopic() {
        return new ModelAndView("add-topic");
    }

    @Override
    public String doAddTopic(TopicAddDTO topicAddDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("topicAddDTO", topicAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.topicAddDTO",
                            bindingResult);

            return "redirect:topics/add";
        }

        topicAddDTO.setUserName(principal.getName());
        try {
            this.topicService.addTopic(topicAddDTO);
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("statusCode", e.getStatusCode());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/error";
        } catch (HabitatNotFoundException e) {
            redirectAttributes.addFlashAttribute("statusCode", e.getStatusCode());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/error";
        } catch (PictureNotFoundException e) {
            redirectAttributes.addFlashAttribute("statusCode", e.getStatusCode());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/error";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/";
    }

    @Override
    public ModelAndView topicDetails(Long id) {
        Topic byId;
        try {
            byId = topicService.findById(id);
        } catch (TopicNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        }
        TopicsDetailsView topicsDetailsView = topicService.mapTopicsDetailsView(byId);

        ModelAndView model = new ModelAndView("topic-details");
        model.addObject("topicsDetailsView", topicsDetailsView);

        return model;
    }

    @Override
    public ModelAndView latestTopic() {
        Long id;
        try {
            id = topicService.findLatestTopic().getId();
        } catch (TopicNotFoundException e) {
            //TODO maybe add emptyUnitTopic or add option to be hidden button to details if no topics
            //TODO Think about better option than send error page
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        }
        return new ModelAndView("redirect:/topics/details/" + id);
    }

    @Override
    public ModelAndView mostCommented() {
        Long id;
        try {
            id = topicService.getMostCommented().getId();
        } catch (TopicNotFoundException e) {
            //TODO maybe add emptyUnitTopic or add option to be hidden button to details if no topics
            //TODO Think about better option than send error page
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;

        }
        return new ModelAndView("redirect:/topics/details/" + id);
    }

    @Override
    public ModelAndView myTopics(Principal principal) {
        List<Topic> allMyApprovedTopics;
        try {
            allMyApprovedTopics = topicService.getAllApprovedMyTopics(principal.getName());
        } catch (TopicNotFoundException e) {
            allMyApprovedTopics = List.of(topicService.getNoTopicUnit());
        } catch (UserNotFoundException e) {
            ModelAndView error = new ModelAndView("/error");
            error.addObject("statusCode", e.getStatusCode());
            error.addObject("message", e.getMessage());
            return error;
        }

        List<TopicsView> allMyApprovedTopicsViews = topicService.mapTopicDetailsViewList(allMyApprovedTopics);

        ModelAndView model = new ModelAndView("topics");
        model.addObject("topics", allMyApprovedTopicsViews);
        return model;
    }

    @Override
    public CommentAddDTO initAddCommentForm() {
        return new CommentAddDTO();
    }

    @Override
    public String doCommentAdd(Long id, CommentAddDTO commentAddDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentAddDTO", commentAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.commentAddDTO",
                            bindingResult);

            return "redirect:topics/" + id;
        }

        commentAddDTO.setAuthorUsername(principal.getName());
        commentAddDTO.setTopicId(id);
        try {
            topicService.addComment(commentAddDTO);
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("statusCode", e.getStatusCode());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/error";
        } catch (TopicNotFoundException e) {
            redirectAttributes.addFlashAttribute("statusCode", e.getStatusCode());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/error";
        }
        return "redirect:topics/" + id;
    }

    @Override
    public PictureAddDTO initAddPictureForm() {
        return new PictureAddDTO();
    }

    @Override
    public String doPictureAdd(Long id, PictureAddDTO pictureAddDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("pictureAddDTO", pictureAddDTO);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.pictureAddDTO",
                            bindingResult);

            return "redirect:topics/" + id;
        }

        pictureAddDTO.setTopicId(id);
        try {
            topicService.addPicture(pictureAddDTO);
        } catch (TopicNotFoundException e) {
            redirectAttributes.addFlashAttribute("statusCode", e.getStatusCode());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/error";
        }
        return "redirect:topics/" + id;
    }
}
