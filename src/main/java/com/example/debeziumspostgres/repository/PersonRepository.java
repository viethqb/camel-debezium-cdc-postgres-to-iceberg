package com.example.debeziumspostgres.repository;

import com.example.debeziumspostgres.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findAllByIsActiveTrue();

    Optional<Person> findByIdAndIsActiveTrue(Long id);
}
