package com.altuncode.mystore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique=true)
    @Email
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable=false)
    private String password;

    @Column(name = "role", nullable=false)
    private String role;

    @CreationTimestamp // Automatically sets the creation date
    @Column(updatable = false) // Prevent updates to this field
    private LocalDateTime createDate;

}
