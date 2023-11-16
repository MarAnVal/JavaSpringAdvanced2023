package bg.softuni.aquagate.repository;

import bg.softuni.aquagate.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    UserEntity findUserByUsername(String username);

    UserEntity findUserByEmail(String email);
}
