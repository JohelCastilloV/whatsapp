package com.xcale.whatsapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    private List<GrantedAuthority> roles = new ArrayList<>();

    @JsonIgnore
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public User() {
        addRole(new SimpleGrantedAuthority("ROLE_USER"));
    }
    public void addRole(GrantedAuthority role) {
        roles.add(role);
    }

    public void addAuthority(GrantedAuthority authority) {
        authorities.add(authority);
    }
    public boolean removeAuthority(GrantedAuthority authority) {
        return authorities.remove(authority);
    }
}
