package com.almaz.almazbank.repositories;

import com.almaz.almazbank.models.MyUser;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByFirstname(String name);
    Boolean existsByFirstname(String name);
    Boolean existsByEmail(String email);
    Optional<MyUser> findByEmail(String email);
}
