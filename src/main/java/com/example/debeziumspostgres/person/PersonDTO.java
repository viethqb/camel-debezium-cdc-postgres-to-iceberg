package com.example.debeziumspostgres.person;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonDTO {

    private String name;

    private Integer age;
}
