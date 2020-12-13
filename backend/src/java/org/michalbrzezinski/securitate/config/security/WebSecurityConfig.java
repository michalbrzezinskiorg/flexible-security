package org.michalbrzezinski.securitate.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.michalbrzezinski.securitate.config.security.StringifiyController.getHttpMethodFromAuthority;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String PUBLIC = "/public";
    private static final String LOGOUT = "/users/logout";
    private static final String SWAGGER = "/swagger-resources/**";

    private static final String[] PUBLIC_PLACES = {PUBLIC, LOGOUT, SWAGGER};
    private final SpringControllersForSecurity springControllersForSecurity;
    private final CustomAuthenticationProvider authenticationProvider;
    @Value("${ldap.user.search.base}")
    private String userSearchBase;
    @Value("${ldap.user.search.filter}")
    private String userSearchFilter;
    @Value("${security.authentication.ldap.url}")
    private String url;
    @Value("${security.authentication.ldap.manager.username}")
    private String username;
    @Value("${security.authentication.ldap.manager.password}")
    private String password;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        log.info("configure(HttpSecurity [{}])", httpSecurity);
        final List<String> publicPlacesAsList = Arrays.asList(PUBLIC_PLACES);
        init(httpSecurity);
        setHttpSecurityForPublicPlaces(httpSecurity);
        setHttpSecurityForAuthorities(httpSecurity, getNonPublicControllers(publicPlacesAsList));
        options(httpSecurity);
        setCsrf(httpSecurity);
        setType(httpSecurity);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userSearchBase(userSearchBase)
                .userSearchFilter(userSearchFilter)
                .groupSearchBase(null)
                .ldapAuthoritiesPopulator((userData, username) -> authenticationProvider.getUserAuthorities(userData, username))
                .contextSource()
                .url(url)
                .managerDn(username)
                .managerPassword(password).and()
                .passwordCompare()
                .passwordAttribute("userpassword")
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    private ArrayList<String> getNonPublicControllers(List<String> publicPlacesAsList) {
        log.info("getNonPublicControllers(List<String>  [{}]))", publicPlacesAsList);
        var controllers = springControllersForSecurity.getControllers();
        var nonPublicControllers = controllers.stream()
                .filter(c -> !publicPlacesAsList.contains(stringifiedControllerToUrl(c))).distinct()
                .collect(Collectors.toCollection(ArrayList::new));
        publicPlacesAsList.forEach(c -> log.info(">>> public controller [{}]", c));
        nonPublicControllers.forEach(c -> log.info(">>> non public controller [{}]", c));
        return nonPublicControllers;
    }

    private String stringifiedControllerToUrl(String s) {
        return s
                .substring(0, s.lastIndexOf("$"))
                .replace("//", "/");
    }

    private void init(HttpSecurity httpSecurity) throws Exception {
        log.info("init(HttpSecurity  [{}]))", httpSecurity);
        httpSecurity
                .headers().frameOptions().sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and();
    }

    private void setHttpSecurityForPublicPlaces(HttpSecurity httpSecurity) throws Exception {
        log.info("setHttpSecurityForPublicPlaces");
        httpSecurity.authorizeRequests().antMatchers(PUBLIC_PLACES).permitAll();
    }

    private void options(HttpSecurity httpSecurity) throws Exception {
        log.info("options");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();
    }

    private void setType(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().logout().invalidateHttpSession(true);
    }

    private void setCsrf(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    private void setHttpSecurityForAuthorities(final HttpSecurity httpSecurity, List<String> nonPublicControllers) {
        nonPublicControllers.forEach(auth -> nonPublicController(httpSecurity, auth));
    }

    private void nonPublicController(HttpSecurity httpSecurity, String auth) {
        HttpMethod httpMethod = getHttpMethodFromAuthority(auth);
        String local = auth.substring(0, auth.lastIndexOf("$"));
        log.info(" >>>> setHttpSecurityForAuthorities httpMethod: [{}] uri: [{}] needs authority: [{}]", httpMethod, local, auth);
        if (httpMethod != null) {
            authorize(httpSecurity, auth, httpMethod, local);
        } else
            log.error("auth: [{}]", auth);
    }

    private void authorize(HttpSecurity httpSecurity, String auth, HttpMethod httpMethod, String local) {
        try {
            httpSecurity.authorizeRequests().antMatchers(httpMethod, local).hasAuthority("ROLE_" + auth);
        } catch (Exception e) {
            log.error("pron [{}]", e.getMessage());
        }
    }
}
