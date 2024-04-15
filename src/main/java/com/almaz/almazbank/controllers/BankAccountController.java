package com.almaz.almazbank.controllers;

import com.almaz.almazbank.models.BankAccount;
import com.almaz.almazbank.models.MyUser;
import com.almaz.almazbank.repositories.BankAccountRepository;
import com.almaz.almazbank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


@RestController
@RequestMapping("/api/bank")
public class BankAccountController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/{id}/create")
    public ResponseEntity<BankAccount> createBankAccount(@PathVariable("id") Long id){
        try{
            Optional<MyUser> _user = userRepository.findById(id);
            if(_user.isPresent()){
                MyUser user = _user.get();
                BankAccount bankAccount = new BankAccount(user);
                bankAccountRepository.save(bankAccount);
                return new ResponseEntity<>(bankAccount,HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            log.error(String.valueOf(e));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<BankAccount> withdrawAmount(@PathVariable("id") Long id, @RequestParam double amount) throws IllegalArgumentException{
        try{
            Optional<BankAccount> _bankAccount = bankAccountRepository.findById(id);
            if(_bankAccount.isPresent()){
                BankAccount bankAccount = _bankAccount.get();
                if(bankAccount.withdraw(amount)) {
                    bankAccountRepository.save(bankAccount);
                    return new ResponseEntity<>(bankAccount,HttpStatus.OK);
                }else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            catch(Exception e){
            log.error("Error is "+e);

            }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/{id}/add")
    public ResponseEntity<BankAccount> addBalance(@PathVariable("id") Long id, @RequestParam double amount){
        try {
            Optional<BankAccount> _bankAccount = bankAccountRepository.findById(id);
            if(_bankAccount.isPresent()){
                BankAccount bankAccount = _bankAccount.get();
                bankAccount.addBalance(amount);
                bankAccountRepository.save(bankAccount);
                return new ResponseEntity<>(bankAccount,HttpStatus.OK);
            }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Error is "+e);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
