package bg.softuni.aquagatedb.web.controller;

import bg.softuni.aquagatedb.data.model.TopicAddDTO;
import bg.softuni.aquagatedb.data.view.TopicDetailsView;
import bg.softuni.aquagatedb.data.view.TopicView;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/topics")
@CrossOrigin("*") //???
public interface TopicsController {

    @GetMapping("/all")
    ResponseEntity<List<TopicView>> getAllTopics();

    @GetMapping("/details/{id}")
    ResponseEntity<TopicDetailsView> getTopicDetails(@PathVariable Long id);

    @DeleteMapping("/remove/{id}")
    ResponseEntity<TopicView> doRemove(@PathVariable Long id);

    @PostMapping("/approve/{id}")
    ResponseEntity<TopicView> doApprove(@PathVariable Long id);

    @PostMapping("/add")
    ResponseEntity<TopicView> doTopicAdd(@RequestBody @Valid TopicAddDTO topicAddDTO, BindingResult bindingResult);

}
