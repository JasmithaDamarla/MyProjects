package com.epam.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="role")
public class Role {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int roleId;
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    List<User> user;

    public Role(){

    }

    public Role(String roleName){
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return user;
    }

    public void setUsers(List<User> user) {
        this.user = user;
    }
}
