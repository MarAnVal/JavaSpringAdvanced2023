package bg.softuni.aquagatedb.data.entity;

import bg.softuni.aquagatedb.data.entity.enumeration.HabitatEnum;
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
}
