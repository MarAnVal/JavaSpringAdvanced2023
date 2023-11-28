package bg.softuni.aquagatedb.data.entity;

import bg.softuni.aquagatedb.data.entity.enumeration.LevelEnum;
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
    @Column(nullable = false)
    private LevelEnum level;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long author;

    @Column(nullable = false)
    private String videoUrl;

    @ManyToOne
    private Picture picture;

    @OneToMany(mappedBy = "topic")
    private List<Comment> comments;

}
