package bg.softuni.aquagate.data.view;

import bg.softuni.aquagate.data.entity.Comment;
import bg.softuni.aquagate.data.entity.Habitat;
import bg.softuni.aquagate.data.entity.Picture;
import bg.softuni.aquagate.data.entity.UserEntity;
import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class TopicView {

    private Long id;

    private Boolean approved;

    private String name;

    private Habitat habitat;

    private LevelEnum level;

    private String description;

    private UserEntity author;

    private String videoUrl;
}
