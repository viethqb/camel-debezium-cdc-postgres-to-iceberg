package com.example.debeziumspostgres.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("people")
public class PersonController {


    private final PersonRepository repository;

    @Autowired
    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(repository.findAllByIsActiveTrue());
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody PersonDTO person) {

        Person data = new Person();
        data.setAge(person.getAge());
        data.setName(person.getName());
        data.setIsActive(Boolean.TRUE);
        return ResponseEntity.ok(repository.save(data));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> put(@PathVariable Long id,
                                 @RequestBody PersonDTO person) {

        var personOp = repository.findByIdAndIsActiveTrue(id);
        return personOp.map(p -> {
            p.setName(person.getName());
            p.setAge(person.getAge());
            return ResponseEntity.ok(repository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var personOp = repository.findByIdAndIsActiveTrue(id);
        return personOp.map(p -> {
            p.setIsActive(Boolean.FALSE);
            repository.save(p);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
