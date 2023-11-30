package bg.softuni.aquagatedb.web.controller;

import bg.softuni.aquagatedb.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagatedb.model.dto.view.TopicDetailsView;
import bg.softuni.aquagatedb.model.dto.view.TopicView;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/topics")
@CrossOrigin("*")
//TODO add corsOrigins only localhost:8080
public interface TopicsController {

    @GetMapping("/all")
    ResponseEntity<List<TopicView>> getAllTopics();

    @GetMapping("/details/{id}")
    ResponseEntity<TopicDetailsView> getTopicDetails(@PathVariable Long id);

    @DeleteMapping("/remove/{id}")
    ResponseEntity<TopicView> doRemove(@PathVariable Long id);

    @PostMapping("/approve/{id}")
    ResponseEntity<TopicDetailsView> doApprove(@PathVariable Long id);

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    ResponseEntity<TopicView> doTopicAdd(@Valid TopicAddDTO topicAddDTO, BindingResult bindingResult);

}
