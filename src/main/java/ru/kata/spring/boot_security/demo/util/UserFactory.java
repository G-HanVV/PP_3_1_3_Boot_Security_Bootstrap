package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;


@Component
public class UserFactory {
    private final RoleService roleService;

    public UserFactory(RoleService roleService) {
        this.roleService = roleService;
    }

    public User getUserToNewUserForm() {
        User user = new User();
        user.setRoles(roleService.findAll());
        return user;
    }
}
