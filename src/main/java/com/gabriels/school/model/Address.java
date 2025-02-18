package com.gabriels.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int addressId;
    @Size(min=5, message = "Address1 must be at least 5 characters long")
    @NotBlank(message = "Address1 must not be blank")
    private String address1;
    @Size(min=5, message="Address2 must be at least 5 characters long")
    @NotBlank(message = "Address2 must not be blank")
    private String address2;
    @Size(min=5, message="City must be at least 5 characters long")
    @NotBlank(message = "City must not be blank")
    private String city;
    @NotBlank(message = "State must not be blank")
    private String state;
    @NotBlank(message = "Zip_Code must not be blank")
    @Pattern(regexp = "(^$|[0-9]{5})", message = "Zip code must be 5 digits")
    private String zip_code;
}
