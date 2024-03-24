package ru.danilenko.telros.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.danilenko.telros.backend.emuns.Roles;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    @Email
    @NotBlank(message = "не должно быть пустым")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "surname")
    @NotBlank(message = "не должно быть пустым")
    private String surname;

    @Column(name = "name")
    @NotBlank(message = "не должно быть пустым")
    private String name;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "date_of_birth")
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    @Pattern(regexp = "^\\+7(\\d{10})$", message = "Формат +7XXXXXXXXX")
    private String phoneNumber;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Roles role;
}
