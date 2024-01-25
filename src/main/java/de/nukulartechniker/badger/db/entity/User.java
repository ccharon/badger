package de.nukulartechniker.badger.db.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "USERS")
public class User {
    @Id
    private long id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "USERS2BADGES",
            joinColumns = { @JoinColumn(name = "USERS_ID") },
            inverseJoinColumns = { @JoinColumn(name = "BADGES_ID") }
    )
    List<Badge> badges = new ArrayList<>();
}
