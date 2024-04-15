package com.almaz.almazbank.controllers;

import com.almaz.almazbank.models.MyUser;
import com.almaz.almazbank.models.UserRecord;
import com.almaz.almazbank.repositories.UserRepository;
import com.almaz.almazbank.services.MyUserService;
import com.almaz.almazbank.utilities.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/create")
    public ResponseEntity<MyUser> createUser(@RequestBody UserRecord user){
        try{
            MyUser user1 = new MyUser(user.firstName(),user.lastName(),user.middleName(),user.email(),user.phoneNumber(),parseDate(user.birthday()),user.nickName());
            String encodedPassword = new BCryptPasswordEncoder().encode(user.password());
            user1.setPassword(encodedPassword);
            userRepository.save(user1);
            log.info("Access has");
         return new ResponseEntity<>(user1, HttpStatus.CREATED);
        }catch (Exception e){
            log.error(String.valueOf(e));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<MyUser> updateUser(@PathVariable("id") Long id,
                                           @RequestBody UserRecord user){
        log.info("Inside update");
        try {
            Optional<MyUser> _user = userRepository.findById(id);
            if (_user.isEmpty()){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
                }
            MyUser myUser = _user.get();
            myUser.setFirstname(user.firstName());
            myUser.setLastname(user.lastName());
            myUser.setMiddlename(user.middleName());
            myUser.setEmail(user.email());
            myUser.setPhoneNumber(user.phoneNumber());
            myUser.setBirthday(parseDate(user.birthday()));
            myUser.setNick(user.nickName());
            myUser.setPassword(passwordEncoder.encode(user.password()));
            userRepository.save(myUser);
            return new ResponseEntity<>(myUser,HttpStatus.OK);
        }catch (Exception e){
            log.error(String.valueOf(e));
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<MyUser>> getAllUsers(@RequestParam(required = false) String firstname){
        try {
            List<MyUser> users = new ArrayList<>();
            if(firstname==null){
                userRepository.findAll().forEach(users::add);
            }else {
                userRepository.findByFirstname(firstname);
            }
            if(users.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users,HttpStatus.OK);
        }catch (Exception e){
            log.info("Something happened ------------>"+e);
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private java.util.Date parseDate(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
    private java.util.Date parseTimestamp(String timestamp) {
        try {
            return DATE_TIME_FORMAT.parse(timestamp);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }



}
