package com.gabriels.school.model;

import com.gabriels.school.annotation.FieldValueMatch;
import com.gabriels.school.annotation.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name="person")
@Data
@FieldValueMatch.List({
        @FieldValueMatch(field = "email", fieldMatch = "confirmEmail", message = "Emails do not match!"),
        @FieldValueMatch(field = "pwd", fieldMatch = "confirmPwd", message = "Passwords do not match!")
})
public class Person extends BaseEntity{

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name should be between 3 to 50 characters")
    private String name;
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Invalid mobile number")
    private String mobileNumber;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
    @NotBlank(message = "Confirm email is required")
    @Email(message = "Invalid email")
    @Transient
    private String confirmEmail;
    @NotBlank(message = "Password is required")
    @PasswordValidator
    private String pwd;
    @NotBlank(message = "Confirm password is required")
    @Transient
    private String confirmPwd;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId", nullable = true)
    private Address address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name="role_id", referencedColumnName = "roleId", nullable = true)
    private Roles roles;

    @ManyToOne(fetch = FetchType.LAZY, optional = true) // its true - As cascade type doesn't affect here, so ignoring
    @JoinColumn(name="class_id",referencedColumnName = "classId",nullable = true) //foreign key relation can be nullable
    private EazyClass eazyClass;
}
