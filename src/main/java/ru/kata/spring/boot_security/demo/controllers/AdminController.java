package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserFactory;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "")
    public String showAdmin(ModelMap model, Principal principal){
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        User user = userService.getUser(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        model.addAttribute("newUser", new User());
        return "admin_bt";
    }

    @GetMapping("/user/edit/{id}")
    @ResponseBody
    public UserDTO getUserForEditing(@PathVariable int id) {
        User user = userService.getUser(id);
        return new UserDTO(user);
    }

    @PostMapping("/user/save")
    public String updateUser(@ModelAttribute User user, @RequestParam Set<Integer> roleIds){
        userService.updateUser(user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping("/user")
    public String createUser(@ModelAttribute("user") User user){
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteUser(Math.toIntExact(id));
        return "redirect:/admin";
    }


    public static class UserDTO {
        private int id;
        private String name;
        private String surname;
        private String age;
        private Set<Integer> roleIds;

        public UserDTO(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.surname = user.getSurname();
            this.age = user.getAge();
            this.roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Set<Integer> getRoleIds() {
            return roleIds;
        }

        public void setRoleIds(Set<Integer> roleIds) {
            this.roleIds = roleIds;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
