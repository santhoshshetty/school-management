package com.gabriels.school.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="contact_msg")
public class Contact extends BaseEntity{

    @Id
    @Column(name = "contact_id")
    private int contactId;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name should be between 3 to 50 characters")
    private String name;

    @NotBlank(message = "Mobile number is required")
    @Size(min = 10, max = 10, message = "Mobile number should be 10 digits")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Invalid mobile number")
    private String mobileNum;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Subject is required")
    @Size(min = 3, max = 100, message = "Subject should be between 3 to 100 characters")
    private String subject;

    @NotBlank(message = "Message is required")
    @Size(min = 5, max = 500, message = "Message should be between 5 to 500 characters")
    private String message;

    private String status;
}
