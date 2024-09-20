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
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/")
//    public String users(){
//        List<User> userList = userService.getUsers();
//        System.out.println(userList.size());
//        return "users";
//    }


//    @GetMapping(value = "/")
//    public String showUsers(ModelMap model){
//        System.out.println(" --- startUser");
//        List<User> users = userService.getUsers();
//        model.addAttribute("users", users);
//        return "/index";
//    }

//    @GetMapping (value = "/form")
//    public String newUserForm(ModelMap model){
//        System.out.println(" --- newUser");
//        model.addAttribute("user", new User());
//        return "/form";
//    }

    @GetMapping(value = "")
    public String showUser(ModelMap model, Principal principal){
        model.addAttribute("user", userService.getUser(principal.getName()));
        return "/user";
    }

//    @PostMapping("/user")
//    public String createUser(@ModelAttribute("user") User user){
//        System.out.println(" --- create");
//        userService.add(user);
//        return "redirect:/";
//    }

//    @GetMapping("/{id}/edit")
//    public String editUser(ModelMap model, @PathVariable("id") int id){
//        System.out.println(" --- editUser");
//        model.addAttribute("user", userService.getUser(id));
//        return "edit";
//    }

//    @PatchMapping("/{id}")
//    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id){
//        System.out.println(" --- updateUser");
//        userService.updateUser(user);
//        return "redirect:/";
//    }

//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable("id") int id){
//        System.out.println(" --- deleteUser");
//        userService.deleteUser(id);
//        return "redirect:/";
//    }
}
