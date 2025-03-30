package com.medom5.springboot.person;

import com.medom5.springboot.SortingOrder;
import com.medom5.springboot.exception.DuplicateResourceException;
import com.medom5.springboot.exception.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(FakePersonRepository fakePersonRepository, PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPeople(
            SortingOrder sort
    ) {
        return personRepository.findAll(
                Sort.by(
                        Sort.Direction.valueOf(sort.name()),
                        "id"
                )
        );
    }


    public Person getPersonById(Integer id) {
        return personRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Person with id: " + id + " does not exist"));

    }

    public void deletePersonById(Integer id) {
        boolean exists = personRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Person with id: " + id + " does not exist");
        }
        personRepository.deleteById(id);
    }

    public void addPerson(NewPersonRequest personRequest) {
        boolean exists = personRepository.existsByEmailIgnoreCase(personRequest.email());

        if (exists) {
            throw new DuplicateResourceException("Email is taken");
        }
        Person p = new Person(
                personRequest.name(),
                personRequest.age(),
                personRequest.gender(),
                personRequest.email()
        );
        personRepository.save(p);
    }

    public void updatePerson(Integer id,
                             PersonUpdateRequest request) {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Person with id " + id + " does not exists"
                ));

        boolean isUpdated = false;

        if (!request.name().equals(person.getName())) {
            person.setName(request.name());
            isUpdated = true;
        }

        if (!request.age().equals(person.getAge())) {
            person.setAge(request.age());
            isUpdated = true;
        }

        if (!request.email().equals(person.getEmail())) {
            person.setEmail(request.email());
            isUpdated = true;
        }

        if (isUpdated) {
            personRepository.save(person);
        }

    }

}
