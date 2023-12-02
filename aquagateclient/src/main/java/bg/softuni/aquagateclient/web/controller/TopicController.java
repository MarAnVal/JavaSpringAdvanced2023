package bg.softuni.aquagateclient.web.controller;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.web.interceptor.annotation.PageTitle;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@RequestMapping("/topics")
public interface TopicController {

    @GetMapping("/")
    @PageTitle("Topics")
    @PreAuthorize("isAuthenticated()")
    ModelAndView allApprovedTopics();

    @ModelAttribute("topicAddDTO")
    TopicAddDTO initAddTopicForm();

    @GetMapping("/add")
    @PageTitle("Add new topic")
    @PreAuthorize("isAuthenticated()")
    ModelAndView addTopic();

    @PostMapping("/add")
    ModelAndView doAddTopic(@Valid TopicAddDTO topicAddDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      Principal principal) throws IOException;

    @GetMapping("/details/{id}")
    @PageTitle("Topic detail")
    @PreAuthorize("isAuthenticated()")
    ModelAndView topicDetails(@PathVariable Long id);

    @GetMapping("/latest")
    @PageTitle("Latest topic")
    @PreAuthorize("isAuthenticated()")
    ModelAndView latestTopicDetails();

    @GetMapping("/my-topics")
    @PageTitle("My allApprovedTopics")
    @PreAuthorize("isAuthenticated()")
    ModelAndView myTopics(Principal principal);

}
