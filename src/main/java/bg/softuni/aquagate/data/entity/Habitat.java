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
public class Habitat extends BaseEntity{

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private HabitatEnum name;

    //id - Accepts UUID String or Long values
    //
    //· name - Accepts String values (PEDESTRIAN, BICYCLE, MOTORCYCLE, CAR)
    //
    //· description - Accepts very long String values
}
