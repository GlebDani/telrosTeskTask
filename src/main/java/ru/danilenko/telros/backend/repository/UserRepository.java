package ru.danilenko.telros.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danilenko.telros.backend.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
