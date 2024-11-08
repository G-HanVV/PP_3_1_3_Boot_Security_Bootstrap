package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.services.RoleService;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "authority")
    private String authority;

    public Role() {
    }

    public static Role getInstance(String id) {
        return RoleService.getRoleService().findById(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthority(String name) {
        this.authority = name;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return authority;
    }

//    public String getName(){
//        return authority;
//    }
//
//    public String getIdString(){
//        return String.valueOf(id);
//    }
}
