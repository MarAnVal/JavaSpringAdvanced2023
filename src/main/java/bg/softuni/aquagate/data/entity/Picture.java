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

    @Column(nullable = false)
    //TODO annotation to accept files only ending with jpeg jpg bmp....
    private String pictureUrl;

    @ManyToOne
    private Topic topic;
}
