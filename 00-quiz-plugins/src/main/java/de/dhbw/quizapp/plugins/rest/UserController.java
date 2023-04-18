package de.dhbw.quizapp.plugins.rest;

import de.dhbw.quizapp.adapter.user.UserMapper;
import de.dhbw.quizapp.adapter.user.UserResource;
import de.dhbw.quizapp.application.user.UserNotFoundException;
import de.dhbw.quizapp.application.user.UserService;
import de.dhbw.quizapp.domain.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public UserResource createUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        User newUser = userService.createUser(username, password, role);
        return userMapper.toResource(newUser);
    }


    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public UserResource getUser(@PathVariable("username") String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return userMapper.toResource(user);
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<UserResource> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return users.stream().map(userMapper::toResource).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
    }
}
