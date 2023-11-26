package bg.softuni.aquagatedb.data.entity;

import bg.softuni.aquagatedb.data.entity.enumeration.LevelEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String videoUrl;
}
