package ru.danilenko.telros.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import org.aspectj.lang.annotation.RequiredTypes;
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
//    @SequenceGenerator(name = "user_id_seq", sequenceName = "")
    private int id;

    @Column(name = "email")
    @Email
    @NotBlank(message = "не должно быть пустым")
//    @Unique
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
//    @NotEmpty
    private Date dateOfBirth;

    @Column(name = "phone_number")
//    @Pattern(regexp = "^\\+7(\\d{10})$", message = "Формат +7XXXXXXXXX")
    private String phoneNumber;

//    @Column(name = "photo_path")
//    private String photoPath;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Roles role;
}
