package com.almaz.almazbank.repositories;

import com.almaz.almazbank.models.BankAccount;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    BankAccount findById(int i);
}
