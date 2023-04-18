package de.dhbw.quizapp.adapter.user;

import de.dhbw.quizapp.domain.role.Role;
import de.dhbw.quizapp.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResource toResource(User user) {
        return new UserResource(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public User toEntity(UserResource userResource) {
        User user = new User();
        user.setId(userResource.getId());
        user.setUsername(userResource.getUsername());
        user.setRole(Role.valueOf(userResource.getRole().toString().toUpperCase()));
        return user;
    }
}
