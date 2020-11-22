package org.michalbrzezinski.securitate.database.security;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "controllers", uniqueConstraints = @UniqueConstraint(columnNames = {"controller", "method", "http"}))
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"permissions", "roles"})
@ToString(exclude = {"permissions", "roles"})
class Controller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String controller;
    private String method;
    private String http;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "controllers")
    private Set<Role> roles;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Permission> permissions;
    @NonNull
    private boolean active;
}