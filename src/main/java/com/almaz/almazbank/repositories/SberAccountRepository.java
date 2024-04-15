package com.almaz.almazbank.repositories;

import com.almaz.almazbank.models.SberAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SberAccountRepository extends JpaRepository<SberAccount,Long> {
    Optional<SberAccount> findById(int id);
}
