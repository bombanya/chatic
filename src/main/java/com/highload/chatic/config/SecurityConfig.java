package com.highload.chatic.config;

import com.highload.chatic.repository.PersonRepository;
import com.highload.chatic.models.Person;
import com.highload.chatic.security.filters.JwtAuthFilter;
import com.highload.chatic.security.filters.JwtVerifyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtVerifyFilter jwtVerifyFilter;
    private final PersonRepository personRepository;

    public SecurityConfig(JwtVerifyFilter jwtVerifyFilter, PersonRepository personRepository) {
        this.jwtVerifyFilter = jwtVerifyFilter;
        this.personRepository = personRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtAuthFilter())
                .addFilterAfter(jwtVerifyFilter, JwtAuthFilter.class)
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(username -> personRepository
                .findByUsername(username)
                .map(this::personToUserDetails)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User " + username + " not found")));
        return provider;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }

    private UserDetails personToUserDetails(Person person) {
        return User.builder()
                .username(person.getUsername())
                .password(person.getPassword())
                .authorities(List.of(person.getAuthRole()))
                .build();
    }
}
