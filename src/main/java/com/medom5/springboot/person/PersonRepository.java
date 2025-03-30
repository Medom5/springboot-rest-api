package com.medom5.springboot.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    boolean existsByEmailIgnoreCase(String email);
}
