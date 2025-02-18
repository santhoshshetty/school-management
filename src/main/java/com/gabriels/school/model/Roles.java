package com.gabriels.school.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.TypeRegistration;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="roles")
public class Roles extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    private int roleId;
    private String roleName;

}
