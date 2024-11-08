package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        System.out.println("UserResource constructor");
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable("userId") int userId) {
        System.out.println("UserResource findById");
        return userService.getUser(userId);
    }

    @PostMapping("")
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public User create(@RequestParam String id, String name, String password, String surname, int age, String role) {
    public User create(@RequestBody User user) {
        System.out.println("UserResource create");
        System.out.println(user);
        return userService.save(user);
    }
}
