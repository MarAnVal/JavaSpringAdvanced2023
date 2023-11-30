package bg.softuni.aquagateclient.model.entity;

import bg.softuni.aquagateclient.model.entity.enumeration.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity{

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Role role)) return false;
        return getName() == role.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
