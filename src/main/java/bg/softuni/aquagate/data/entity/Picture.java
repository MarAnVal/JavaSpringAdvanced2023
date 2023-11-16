package bg.softuni.aquagate.data.entity;

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
public class Picture extends BaseEntity{

    @Column
    private String title;

    @Column(nullable = false)
    private String pictureUrl;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private Topic topic;
}
