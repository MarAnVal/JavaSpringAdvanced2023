package bg.softuni.aquagate.data.entity;

import bg.softuni.aquagate.data.entity.enumeration.HabitatEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Habitat extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HabitatEnum name;

    @Column(nullable = false)
    private String titleText;

    @Column(nullable = false)
    private String bodyText;

    @Column(nullable = false)
    private String iconUrl;
}
