package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.WebSecurityConfig;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        user.getAuthorities().isEmpty();
        return user;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void add(User user) {
        user.setPassword(WebSecurityConfig.passwordEncoder.encode(user.getPassword()));
//        user.setRoles(List.of(roleRepository.getById(2)));
        userRepository.save(user);
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateUser(User user) {
        Objects.requireNonNull(userRepository.findById(user.getId()).orElse(null)).update(user);
    }

    @Transactional
    public void updateUser(User user, Set<Integer> roleIds) {
        System.out.println("UserService --- update user");
        List<Role> roles = roleRepository.findAll().stream().filter(r -> roleIds.contains(r.getId())).collect(Collectors.toList());
        Objects.requireNonNull(userRepository.findById(user.getId()).orElse(null)).update(user, roles);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User getUser(String name) {
        return userRepository.findByName(name);
    }

    public User save(User user) {
        user.setPassword(WebSecurityConfig.passwordEncoder.encode(user.getPassword()));
//        user.setRoles(List.of(roleRepository.getById(2)));
        userRepository.save(user);
        return user;
    }
}
