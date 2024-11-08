package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserFactory;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/bt")
public class BootstrapController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserFactory userFactory;

    public BootstrapController(UserService userService, RoleService roleService, UserFactory userFactory) {
        this.userService = userService;
        this.roleService = roleService;
        this.userFactory = userFactory;
    }

    @GetMapping(value = "")
    public String index(Model model) {

        List<User> users = userService.getUsers();
        model.addAttribute("users", users);

//        User user = userService.getUserToNewUserForm(principal.getName());
        User user = users.get(0);
//        model.addAttribute("newUser", new User());
        model.addAttribute("user", user);
        model.addAttribute("title", "Bootstrap Demo");
        User user1 = userFactory.getUserToNewUserForm();
        Collection<? extends GrantedAuthority> authorities = user1.getAuthorities();
        model.addAttribute("newUser", userFactory.getUserToNewUserForm());
        model.addAttribute("roles", roleService.findAll());
        return "bootstrap_index";
    }
}
