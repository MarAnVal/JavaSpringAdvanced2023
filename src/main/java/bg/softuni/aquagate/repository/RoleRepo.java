package bg.softuni.aquagate.repository;

import bg.softuni.aquagate.data.entity.Role;
import bg.softuni.aquagate.data.entity.enumeration.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum value);

    Optional<Role> findRoleByName(RoleEnum roleEnum);
}
