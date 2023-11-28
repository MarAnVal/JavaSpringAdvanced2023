package bg.softuni.aquagatedb.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Picture extends BaseEntity {

    @Column(nullable = false)
    private String pictureUrl;
}
