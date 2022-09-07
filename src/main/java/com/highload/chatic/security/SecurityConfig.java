package com.highload.chatic.security;

import com.highload.chatic.dao.PersonRepo;
import com.highload.chatic.models.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    private final PersonRepo personRepo;
    private final JwtVerifyFilter jwtVerifyFilter;

    public SecurityConfig(PersonRepo personRepo, JwtVerifyFilter jwtVerifyFilter) {
        this.personRepo = personRepo;
        this.jwtVerifyFilter = jwtVerifyFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(
                        username -> personRepo
                                .findByUsername(username)
                                .map(this::personToUserDetails)
                                .orElseThrow(() ->{
                                            System.out.println("here");
                                            throw new UsernameNotFoundException("User " + username + " not found");
                                }
                                        ))
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //.addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private UserDetails personToUserDetails(Person person) {
        return User.builder()
                .username(person.getUsername())
                .password(person.getPassword())
                .authorities(person.getAuthRoles()
                        .stream()
                        .map(authRoleEntity ->
                                new SimpleGrantedAuthority("ROLE_" + authRoleEntity
                                        .getName()
                                        .name()))
                        .toList())
                .build();
    }
}
