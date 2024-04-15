package com.almaz.almazbank.repositories;

import com.almaz.almazbank.models.SberUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SberUserRepository extends JpaRepository<SberUser, Long> {
    Optional<SberUser> findByLogin(String login);

}
