package bg.softuni.aquagate.web.controller;

import bg.softuni.aquagate.data.model.CommentAddDTO;
import bg.softuni.aquagate.data.model.PictureAddDTO;
import bg.softuni.aquagate.data.model.TopicAddDTO;
import bg.softuni.aquagate.web.interceptor.annotation.PageTitle;
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
    ModelAndView topics();

    @ModelAttribute("topicAddDTO")
    TopicAddDTO initAddTopicForm();

    @GetMapping("/add")
    @PageTitle("Add new topic")
    @PreAuthorize("isAuthenticated()")
    ModelAndView addTopic();

    @PostMapping("/add")
    String doAddTopic(@Valid TopicAddDTO topicAddDTO,
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
    ModelAndView latestTopic();

    @GetMapping("/most-popular")
    @PageTitle("Most popular topic")
    @PreAuthorize("isAuthenticated()")
    ModelAndView mostCommented();

    @GetMapping("/my-topics")
    @PageTitle("My topics")
    @PreAuthorize("isAuthenticated()")
    ModelAndView myTopics(Principal principal);

    @ModelAttribute("commentAddDTO")
    CommentAddDTO initAddCommentForm();

    @PostMapping("/comments/add/{id}")
    String doCommentAdd(@PathVariable Long id, @Valid CommentAddDTO commentAddDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Principal principal);

    @ModelAttribute("pictureAddDTO")
    PictureAddDTO initAddPictureForm();

    @PostMapping("/pictures/add/{id}")
    String doPictureAdd(@PathVariable Long id, @Valid PictureAddDTO pictureAddDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes);
}
