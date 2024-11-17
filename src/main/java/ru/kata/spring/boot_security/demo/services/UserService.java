package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.WebSecurityConfig;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public interface UserService extends UserDetailsService {

    List<User> getUsers();

    void addUser(User user);

    User getUser(int id);

    User getUser(String name);

    void updateUser(User user, Set<Integer> roleIds);

    void deleteUser(int id);
}
