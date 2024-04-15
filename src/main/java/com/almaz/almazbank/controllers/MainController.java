package com.almaz.almazbank.controllers;

import com.almaz.almazbank.models.*;
import com.almaz.almazbank.repositories.SberAccountRepository;
import com.almaz.almazbank.repositories.SberUserRepository;
import com.almaz.almazbank.services.SberUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/sber")
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SberUserRepository sberUserRepository;
    @Autowired
    private SberAccountRepository sberAccountRepository;
    @Autowired
    private SberUserService sberUserService;
    @PostMapping("/create")
    public ResponseEntity<HashMap<SberUser, SberAccount>> createUserWithBankAccount(@RequestBody SberUserRecord bankUserInfo){
        String name = getSurNameNameMiddle(bankUserInfo.surname(),bankUserInfo.name(),bankUserInfo.middle_name());
        Date birthday = parseDate(bankUserInfo.birthday());
//        List<String> phoneNumbers = sberUserService.convertPhoneNumberToList(bankUserInfo.phoneNumbers());
        String encodedPassword = passwordEncoder.encode(bankUserInfo.password());
        Optional<SberUser> sberUserFromDB = sberUserRepository.findByLogin(bankUserInfo.login());
        if (sberUserFromDB.isEmpty()){
            SberUser newSberUser = new SberUser(
                    bankUserInfo.login(),
                    name,
                    birthday,
                    bankUserInfo.phoneNumbers(),
                    bankUserInfo.emails(),
                    encodedPassword
                    );
            SberAccount newSberAccount = new SberAccount(
                    newSberUser,
                    Double.parseDouble(bankUserInfo.balanceOnSberAccount()));
            var savedSberUser = sberUserRepository.save(newSberUser);
            var savedSberAccount = sberAccountRepository.save(newSberAccount);
            HashMap<SberUser, SberAccount> userAndAccount = new HashMap<>();
            userAndAccount.put(savedSberUser, savedSberAccount);
            return new ResponseEntity<>(userAndAccount, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
    @PutMapping(value = "/update/add-phoneNumber/{login}")
    public ResponseEntity<List<String>> updatePhoneNumber(@RequestBody String phone,
                                                          @RequestParam(name = "login") String login) {
        List<String> phones = new ArrayList<>();
        Optional <SberUser> sberUser = sberUserRepository.findByLogin(login);
        if (sberUser.isPresent()){
            SberUser user = sberUser.get();
            user.setPhoneNumbers(phone);
        }else {
            throw new NoSuchElementException();
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/phones/{login}")
    public List<String> getPhoneNumbers(@PathVariable("login") String login) throws UsernameNotFoundException {
        SberUser sberUser = sberUserService.existByLogin(login);
        return sberUserService.convertPhoneNumberToList(sberUser.getPhoneNumbers());
    }

//    utils
    private static String getSurNameNameMiddle(String surname, String name, String middle_name){
        return String.format("%s %s %s", surname,name,middle_name);
    }
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static java.util.Date parseDate(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }


}
