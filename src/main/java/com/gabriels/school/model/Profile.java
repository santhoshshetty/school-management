package com.gabriels.school.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Profile {

    @NotBlank(message = "Name must not be blank")
    @Size(min=3, message="Name must be more than 3 characters")
    private String name;

    @NotBlank(message="Mobile Number must not be blank")
    @Pattern(regexp="^$|[0-9]{10}",message="Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide valid email address")
    private String email;

    @NotBlank(message = "Address1 must not be blank")
    @Size(min=3, message = "Address1 must be more than 3 characters")
    private String address1;

    private String address2;

    @NotBlank(message = "City must not be blank")
    @Size(min=3, message = "City must contain more than 3 characters")
    private String city;

    @NotBlank(message = "State must not be blank")
    private String state;

    @NotBlank(message="Zip Code must not be blank")
    @Size(min=5, message = "Zip code must contain more than 5 digits")
    private String zipCode;

}
