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
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserFactory userFactory;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, UserFactory userFactory, RoleService roleService) {
        this.userService = userService;
        this.userFactory = userFactory;
        this.roleService = roleService;
    }

    @GetMapping(value = "")
    public String showAdmin(ModelMap model, Principal principal){
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);

        User user = userService.getUser(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("allRoles", roleService.findAll());
        User newUser = userFactory.getUserToNewUserForm();
        System.out.println(newUser.getRoles());
//        model.addAttribute("newUser", new User());
        model.addAttribute("newUser", userFactory.getUserToNewUserForm());
//        return "bootstrap_admin";
        return "bootstrap_index";
//        return "/admin";
    }

    @GetMapping("/user/edit/{id}")
    @ResponseBody
    public UserDTO getUserForEditing(@PathVariable int id) {
        User user = userService.getUser(id);
//        Set<Role> roles = user.getRoles();
        // Преобразуем пользователя в DTO
        return new UserDTO(user);
    }

    @GetMapping (value = "/form")
    public String newUserForm(ModelMap model){
        System.out.println(" --- newUser");
        model.addAttribute("user", userFactory.getUserToNewUserForm());
        model.addAttribute("roles", roleService.findAll());
        return "/form";
    }

//    @PostMapping("/user/save")
    public String saveUser(@ModelAttribute User user, @RequestParam Set<Integer> roleIds) {
        System.out.println(" --- saveUser");
        System.out.println(user);
//        users.put(user.getId(), user);
        userService.updateUser(user);
        return "redirect:/users";
    }

    //    @PatchMapping("/{id}")
    @PostMapping("/user/save")
    public String updateUser(@ModelAttribute User user, @RequestParam Set<Integer> roleIds){
        System.out.println(" --- updateUser");
        System.out.println(user);
        System.out.println(roleIds);
        userService.updateUser(user, roleIds);
        return "redirect:/admin";
    }

//    @PostMapping("/user/save")
//    public String saveUser(@ModelAttribute User user, @RequestParam Set<Long> roleIds) {
//        Set<Role> roles = roleRepository.findAllById(roleIds);
//        user.setRoles(roles);
//        userRepository.save(user);
//        return "redirect:/users";
//    }


    @PostMapping("/user")
    public String createUser(@ModelAttribute("user") User user){
        System.out.println(" --- create");
//        System.out.println("roles = " + roles);
        System.out.println(user);
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(ModelMap model, @PathVariable("id") int id){
        System.out.println(" --- editUser");
        model.addAttribute("user", userService.getUser(id));
        return "modal_edit";
    }

//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable("id") int id){
//        System.out.println(" --- deleteUser");
//        userService.deleteUser(id);
//        return "redirect:/admin";
//    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              ModelMap model) {
        System.out.println(" --- deleteUser");
        if (action.equals("delete")){
            userService.deleteUser(Math.toIntExact(userId));
        }
        return "redirect:/admin";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id){
        System.out.println(" --- deleteUser 3");
        userService.deleteUser(Math.toIntExact(id));
        return "redirect:/admin";
    }

    // DTO для передачи данных о пользователе
    public static class UserDTO {
        private int id;
        private String name;
        private String surname;
        private String age;
        private Set<Integer> roleIds;

        // Конструктор для создания DTO из сущности User
        public UserDTO(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.surname = user.getSurname();
            this.age = user.getAge();
//            this.email = user.getEmail();
            this.roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        }

        // Геттеры и сеттеры
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

//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }

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
