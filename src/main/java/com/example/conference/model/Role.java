package com.example.conference.model;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
