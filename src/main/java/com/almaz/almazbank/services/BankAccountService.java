package com.almaz.almazbank.services;

import com.almaz.almazbank.models.BankAccount;
import com.almaz.almazbank.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
    public BankAccount saveIncrementedBalance(int id){
        BankAccount bankAccount = bankAccountRepository.findById(id);
        bankAccount.increment();
        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }
}
