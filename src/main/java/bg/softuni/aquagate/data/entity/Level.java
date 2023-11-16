package bg.softuni.aquagate.data.entity;

import bg.softuni.aquagate.data.entity.enumeration.LevelEnum;
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
public class Level extends BaseEntity{

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private LevelEnum name;
}
