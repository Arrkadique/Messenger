package com.arrkadique.springmessenger.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Slf4j
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String username;

    private String password;

    private String email;

    private String avatar;

    private String color;

    private String colorHover;

    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
