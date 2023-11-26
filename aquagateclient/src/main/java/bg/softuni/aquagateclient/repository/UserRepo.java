package bg.softuni.aquagateclient.repository;

import bg.softuni.aquagateclient.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByUsername(String name);

    Optional<UserEntity> findUserByEmail(String value);
}
