package com.gabriels.school.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name="class")
public class EazyClass extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message="Name must contain minimum of 3 characters")
    private String name;

    @OneToMany(mappedBy="eazyClass",fetch = FetchType.LAZY,
            cascade=CascadeType.PERSIST, targetEntity = Person.class)
    private Set<Person> persons;
}
