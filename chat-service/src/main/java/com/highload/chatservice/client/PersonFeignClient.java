package com.highload.chatservice.client;

import com.highload.chatservice.client.shared.PersonResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("person-service")
public interface PersonFeignClient {
    @GetMapping(path = "/persons/{username}")
    PersonResponseDto getPerson(@PathVariable(value = "username") String username);
}