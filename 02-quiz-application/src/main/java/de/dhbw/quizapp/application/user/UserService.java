package de.dhbw.quizapp.application.user;

import de.dhbw.quizapp.domain.repository.user.UserRepository;
import de.dhbw.quizapp.domain.role.Role;
import de.dhbw.quizapp.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String password, String role) {
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setRole(Role.valueOf(role.toUpperCase()));
        return userRepository.save(newUser);
    }

    public User updateUser(UUID id, String password, String role) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password != null && !password.isEmpty()) {
                String encodedPassword = passwordEncoder.encode(password);
                user.setPassword(encodedPassword);
            }
            if (role != null && !role.isEmpty()) {
                user.setRole(Role.valueOf(role.toUpperCase()));
            }
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
