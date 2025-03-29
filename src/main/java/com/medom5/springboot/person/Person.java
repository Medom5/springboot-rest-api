package com.medom5.springboot.person;

public record Person(Integer id,
                     String name,
                     Integer age,
                     Gender gender) {

}
