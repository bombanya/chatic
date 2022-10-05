package com.highload.personservice.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "person.repository")
public record SqlHolder(
        String findById,
        String findByUsername,
        String deleteById
) {
}
