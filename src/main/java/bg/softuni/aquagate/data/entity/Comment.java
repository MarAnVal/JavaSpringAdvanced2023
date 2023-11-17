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
public class Comment extends BaseEntity{

    @Column(nullable = false)
    private String context;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private Topic topic;
}