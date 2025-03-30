package com.medom5.springboot.person;

import com.medom5.springboot.SortingOrder;
import com.medom5.springboot.exception.InvalidSortingOrderException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getPeople(
            @RequestParam(
                    value = "sort",
                    required = false,
                    defaultValue = "ASC"
            ) String sort) {
        try {
            SortingOrder sortingOrder = SortingOrder.valueOf(sort.toUpperCase());

            List<Person> people = personService.getPeople(sortingOrder);

            return ResponseEntity.ok(people);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidSortingOrderException("Invalid sorting order");
        }
    }


    @GetMapping("{id}")
    public ResponseEntity<Person> getPersonById(
            @Valid @Positive @PathVariable("id") Integer id
    ) {
        Person person = personService.getPersonById(id);
        return ResponseEntity.ok().body(person);
    }

    @DeleteMapping("{id}")
    public void deletePersonById(@Valid @Positive @PathVariable("id") Integer id) {
        personService.deletePersonById(id);
    }

    @PostMapping
    public void addPerson(@Valid @RequestBody NewPersonRequest person) {
        personService.addPerson(person);
    }

    @PutMapping("{id}")
    public void updatePerson(@PathVariable("id") Integer id,
                             @Valid @RequestBody PersonUpdateRequest request) {
        personService.updatePerson(id, request);
    }

}
