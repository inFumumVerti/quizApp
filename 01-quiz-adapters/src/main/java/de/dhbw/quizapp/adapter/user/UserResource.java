package de.dhbw.quizapp.adapter.user;

import de.dhbw.quizapp.domain.role.Role;

import java.util.UUID;

public class UserResource {

    private final UUID id;
    private final String username;
    private final Role role;

    public UserResource(UUID id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }
}
