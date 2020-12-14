package org.michalbrzezinski.securitate.database.security;

import lombok.*;
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
class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private boolean active;
    private String createdBy;
    @ManyToMany(fetch = FetchType.LAZY, cascade = ALL)
    @Fetch(FetchMode.JOIN)
    @Singular
    private Set<ControllerEntity> controllers;
}