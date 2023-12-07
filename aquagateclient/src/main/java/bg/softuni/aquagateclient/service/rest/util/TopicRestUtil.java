package bg.softuni.aquagateclient.service.rest.util;

import bg.softuni.aquagateclient.model.dto.binding.TopicAddDTO;
import bg.softuni.aquagateclient.model.dto.request.TopicRequestAddDTO;
import bg.softuni.aquagateclient.model.dto.view.TopicView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;

import java.util.List;

public record TopicRestUtil(String topicsAllUrlSource, String topicAddUrlSource, String topicDetailsUrlSource,
                            String topicRemoveUrlSource, String topicApproveUrlSource) {
    public HttpEntity<TopicRequestAddDTO> getHttpAddTopic(TopicAddDTO topicAddDTO, String pictureUrl) {
        TopicRequestAddDTO topicRequestAddDTO = mapTopicRequestAddDTO(topicAddDTO, pictureUrl);
        return new HttpEntity<>(topicRequestAddDTO);
    }

    public ParameterizedTypeReference<List<TopicView>> getParameterizedTypeReferenceTopicViewList(){
       return new ParameterizedTypeReference<>() {
        };
    }

    private TopicRequestAddDTO mapTopicRequestAddDTO(TopicAddDTO topicAddDTO, String pictureUrl){
        TopicRequestAddDTO topicRequestAddDTO = new TopicRequestAddDTO();
        topicRequestAddDTO.setPictureUrl(pictureUrl);
        topicRequestAddDTO.setVideoUrl(topicAddDTO.getVideoUrl());
        topicRequestAddDTO.setName(topicAddDTO.getName());
        topicRequestAddDTO.setDescription(topicAddDTO.getDescription());
        topicRequestAddDTO.setAuthor(topicAddDTO.getAuthor());
        topicRequestAddDTO.setHabitat(topicAddDTO.getHabitat());
        topicRequestAddDTO.setLevel(topicAddDTO.getLevel());

        return topicRequestAddDTO;
    }
}
