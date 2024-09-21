package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String showAdmin(ModelMap model, Principal principal){
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "bootstrap_admin";
//        return "/admin";
    }

    @GetMapping (value = "/form")
    public String newUserForm(ModelMap model){
        System.out.println(" --- newUser");
        model.addAttribute("user", new User());
        return "/form";
    }

    @PostMapping("/user")
    public String createUser(@ModelAttribute("user") User user){
        System.out.println(" --- create");
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(ModelMap model, @PathVariable("id") int id){
        System.out.println(" --- editUser");
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id){
        System.out.println(" --- updateUser");
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id){
        System.out.println(" --- deleteUser");
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
