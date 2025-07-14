package com.example.todo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authorities")
public class Role {

    @Id
    private Long id;

    private RoleType authority;

    @ReadOnlyProperty
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(authority.name());
    }

    public static Role fromAuthority(RoleType authority) {
        Role role = new Role();
        role.setAuthority(authority);
        return role;
    }

}
