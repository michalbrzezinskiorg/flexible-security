package org.michalbrzezinski.securitate.config.security;

import lombok.RequiredArgsConstructor;
import org.michalbrzezinski.securitate.config.security.objects.Actor;
import org.michalbrzezinski.securitate.feature.security.DatabaseForSecurityManagement;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(value = SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class LoggedUser {

    private final DatabaseForSecurityManagement databaseForSecurityManagement;

    public Optional<Actor> getLoggedUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return databaseForSecurityManagement.getUserByLogin(login).map(u ->
                Actor.builder()
                        .active(u.isActive())
                        .id(u.getId())
                        .login(u.getLogin())
                        .name(u.getName())
                        .surname(u.getSurname())
                        .build()
        );
    }
}

