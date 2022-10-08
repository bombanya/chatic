package com.highload.personservice.config;

import com.highload.personservice.models.Device;
import com.highload.personservice.models.Person;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ConfigurationPropertiesScan("com.highload.personservice")
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SimpleJdbcInsert jdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate);
    }

    @Bean(name = "personMapper")
    public RowMapper<Person> personRowMapper() {
        return new BeanPropertyRowMapper<>(Person.class, true);
    }

    @Bean(name = "deviceMapper")
    public RowMapper<Device> deviceRowMapper() {
        return new BeanPropertyRowMapper<>(Device.class, true);
    }
}
