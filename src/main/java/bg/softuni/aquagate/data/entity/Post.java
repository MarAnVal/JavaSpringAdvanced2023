package bg.softuni.aquagate.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends BaseEntity{

    @Column(nullable = false)
    private Boolean approved;

    @Column(nullable = false)
    private LocalDate created;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private Topic topic;
}
