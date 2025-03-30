package com.medom5.springboot.person;

import com.medom5.springboot.SortingOrder;
import com.medom5.springboot.exception.DuplicateResourceException;
import com.medom5.springboot.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPeople(
            SortingOrder sort
    ) {
        if (sort == SortingOrder.ASC) {
            return personRepository.getPeople().stream()
                    .sorted(Comparator.comparing(Person::id))
                    .collect(Collectors.toList());
        }
        return personRepository.getPeople().stream()
                .sorted(Comparator.comparing(Person::id).reversed())
                .collect(Collectors.toList());
    }


    public Person getPersonById(Integer id) {
        return personRepository.getPeople().stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Person with id: " + id + " does not exist"));

    }

    public void deletePersonById(Integer id) {
        Person p = personRepository.getPeople().stream()
                .filter(person -> person.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Person with id: " + id + " does not exists"
                ));

        personRepository.getPeople().remove(p);
    }

    public void addPerson(NewPersonRequest person) {
        if (person.email() != null && !person.email().isEmpty()) {
            boolean exists = personRepository.getPeople().stream()
                    .anyMatch(p -> p.email().equalsIgnoreCase(person.email()));

            if (exists) {
                throw new DuplicateResourceException("Email is taken");
            }else {
                personRepository.getPeople().add(
                        new Person(
                                personRepository.getIdCounter().incrementAndGet(),
                                person.name(),
                                person.age(),
                                person.gender(),
                                person.email()
                        )
                );
            }
        }
    }

    public void updatePerson(Integer id,
                             PersonUpdateRequest request) {
        Person p = personRepository.getPeople().stream()
                .filter(person -> person.id().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Person with id " + id + " does not exists"
                        ));

        var index = personRepository.getPeople().indexOf(p);

        Person updatedPerson = new Person(
                p.id(),
                p.name(),
                p.age(),
                p.gender(),
                p.email()
        );
        if (request.name() != null && !request.name().isEmpty() && !request.name().equals(p.name())) {
            updatedPerson = new Person(updatedPerson.id(), request.name(), updatedPerson.age(), updatedPerson.gender(), updatedPerson.email());
        }

        if (request.age() != null && !request.age().equals(p.age())) {
            updatedPerson = new Person(updatedPerson.id(), updatedPerson.name(), request.age(), updatedPerson.gender(), updatedPerson.email());
        }

        if (request.email() != null && !request.email().equals(p.email())) {
            updatedPerson = new Person(updatedPerson.id(), updatedPerson.name(), updatedPerson.age(), updatedPerson.gender(), request.email());
        }

        if (!updatedPerson.equals(p)) {
            personRepository.getPeople().set(index, updatedPerson);
        }

    }

}
