package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleService {
    static RoleService roleService;
    private final RoleRepository roleRepository;

    public static RoleService getRoleService() {
        return roleService;
    }

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        roleService = this;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(String id) {
        return roleRepository.findById(Integer.parseInt(id)).orElse(null);
    }
}
