package ru.danilenko.telros.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilenko.telros.backend.entity.security.AuthInfo;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo, Integer> {
}
