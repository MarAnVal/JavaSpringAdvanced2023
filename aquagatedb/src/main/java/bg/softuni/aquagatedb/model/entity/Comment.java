package bg.softuni.aquagatedb.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Column(nullable = false)
    private String context;

    @Column(nullable = false)
    private Long authorId;

    @ManyToOne
    private Topic topic;
}
