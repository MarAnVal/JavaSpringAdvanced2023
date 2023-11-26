package bg.softuni.aquagateclient.repository;

import bg.softuni.aquagateclient.data.entity.Role;
import bg.softuni.aquagateclient.data.entity.enumeration.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(RoleEnum value);
}