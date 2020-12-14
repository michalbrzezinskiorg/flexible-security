package org.michalbrzezinski.securitate.database.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "controllers", uniqueConstraints = @UniqueConstraint(columnNames = {"controller", "method", "http"}))
@NoArgsConstructor
@AllArgsConstructor
class ControllerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String controller;
    private String method;
    private String http;
    @NonNull
    private boolean active;
}