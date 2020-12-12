package org.michalbrzezinski.securitate.database.security;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Builder
@Table(name = "permissions")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"controllers"})
@ToString(exclude = {"controllers"})
class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private boolean active;
    @ManyToOne(fetch = FetchType.LAZY, cascade = ALL)
    @JoinColumn(name = "created_by")
    @Fetch(FetchMode.JOIN)
    private UserEntity createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_for")
    @Fetch(FetchMode.JOIN)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private UserEntity permissionFor;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions", cascade = ALL)
    @Fetch(FetchMode.JOIN)
    @Singular
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<ControllerEntity> controllers;

}