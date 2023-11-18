package bg.softuni.aquagate.data.entity;

import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Topic extends BaseEntity {

    @Column(nullable = false)
    private Boolean approved;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Habitat habitat;

    @Enumerated(EnumType.STRING)
    private LevelEnum level;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private UserEntity author;

    @OneToMany
    private List<Picture> pictures;

    @Column
    private String videoUrl;

    @OneToMany
    private List<Comment> comments;

}
