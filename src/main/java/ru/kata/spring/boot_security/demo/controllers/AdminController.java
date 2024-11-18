package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Set;

//This is JS branch
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleService roleService) {
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
    public String updateUser(@ModelAttribute User user, @RequestParam(required = false) Set<Integer> roleIds){
        userService.updateUser(user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping("/user")
    public String createUser(@ModelAttribute("user") User user){
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteUser(Math.toIntExact(id));
        return "redirect:/admin";
    }
}
