package bg.softuni.aquagate.data.entity;

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
    private String name;

    @ManyToOne
    private Habitat habitat;

    @ManyToOne
    private Level level;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private UserEntity author;

    @OneToMany
    private List<Picture> pictures;

    @Column
    private String videoUrl;

    @OneToMany
    private List<Post> posts;
    //id - Accepts UUID String or Long values
    //
    //· gpx coordinates - Accepts very long text values
    //
    //· level - Accepts the levels of the routes (BEGINNER, INTERMEDIATE, ADVANCED) as values
    //
    //· name - Accepts String values
    //
    //· author - Accepts User Entities as values
    //
    //· video url – Accepts the ids of youtube videos as values

}
