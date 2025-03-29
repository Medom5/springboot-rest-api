package com.medom5.springboot.person;

public record PersonUpdateRequest(
        String name,
        Integer age
) {
}
