package com.gabriels.school.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Getter
@Entity
@Data
@Table(name = "holidays")
public class Holiday {

    @Id
    private String day;
    private String reason;
    //In db this field is varchar (string), but in the code we have it as enum. So to have this adopted add this annotation
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type{
        FESTIVAL, FEDERAL
    }

}
