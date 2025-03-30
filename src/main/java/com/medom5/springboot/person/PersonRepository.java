package com.medom5.springboot.person;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PersonRepository {

    private final AtomicInteger idCounter =
            new AtomicInteger(0);

    private final List<Person> people = new ArrayList<>();

    {
        people.add(
                new Person(
                        idCounter.incrementAndGet(),
                        "John",
                        20,
                        Gender.MALE,
                        "john@jj.cc"
                )
        );
        people.add(
                new Person(
                        idCounter.incrementAndGet(),
                        "Mariam",
                        18,
                        Gender.FEMALE,
                        "mariam@gmail.com")
        );
        people.add(
                new Person(
                        idCounter.incrementAndGet(),
                        "Samba",
                        30,
                        Gender.MALE,
                        "samba@gmail.com")
        );
    }

    public AtomicInteger getIdCounter() {
        return idCounter;
    }

    public List<Person> getPeople() {
        return people;
    }
}
