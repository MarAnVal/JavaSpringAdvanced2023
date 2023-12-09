package bg.softuni.aquagateclient.service;

import bg.softuni.aquagateclient.model.entity.Role;
import bg.softuni.aquagateclient.model.entity.UserEntity;
import bg.softuni.aquagateclient.web.error.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            UserEntity user = userService.findUserByUsername(username);
            return User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().stream()
                            .map(Role::getName)
                            .map(Enum::toString)
                            .toArray(String[]::new))
                    .build();
        } catch (ObjectNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

    }
}
