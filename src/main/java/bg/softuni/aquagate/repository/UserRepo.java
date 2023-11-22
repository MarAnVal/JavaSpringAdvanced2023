package bg.softuni.aquagate.repository;

import bg.softuni.aquagate.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByUsername(String username);

    Optional<UserEntity> findUserByEmail(String email);
}
