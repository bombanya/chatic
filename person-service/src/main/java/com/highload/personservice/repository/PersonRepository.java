package com.highload.personservice.repository;

import com.highload.personservice.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PersonRepository {

    private final SimpleJdbcInsert jdbcInsert;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SqlHolder sqlHolder;

    @Autowired
    @Qualifier("personMapper")
    private RowMapper<Person> personRowMapper;

    public Optional<Person> findById(UUID id) {
        return jdbcTemplate.queryForStream(sqlHolder.findById(),
                Map.of("id", id),
                personRowMapper)
                .findFirst();
    }

    public Optional<Person> findByUsername(String username) {
        return jdbcTemplate.queryForStream(sqlHolder.findByUsername(),
                Map.of("username", username),
                personRowMapper)
                .findFirst();
    }

    public void save(Person person) {
        person.setId(UUID.randomUUID());
        jdbcInsert.withTableName("person")
                .execute(new BeanPropertySqlParameterSource(person));
    }

    public void delete(Person person) {
        jdbcTemplate.update(sqlHolder.deleteById(), Map.of("id", person.getId()));
    }
}