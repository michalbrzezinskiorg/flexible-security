package org.michalbrzezinski.securitate.database.security;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"controllers", "users"})
@ToString(exclude = {"controllers", "users"})
class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, cascade = ALL)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "role_controller",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "controller_id")}
    )
    @Singular
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<ControllerEntity> controllers;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = ALL)
    @Singular
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<UserEntity> users;
    private Boolean active;
}