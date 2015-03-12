package com.flavien.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
     
    @Id
    @Column(name="username")
    private String username;
     
    @Column(name="password")
    private String password;
     
    @Column(name="role_id")  
    private int roleId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}  
	
	public String getRole() {
		 
        String role = null;
 
        if (roleId == 1) {
            role = "ROLE_ADMIN";
        } else if (roleId == 2) {
            role = "ROLE_USER";
        }
        return role;
    }
}