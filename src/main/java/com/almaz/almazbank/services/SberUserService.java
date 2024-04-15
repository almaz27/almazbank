package com.almaz.almazbank.services;

import com.almaz.almazbank.models.SberUser;
import com.almaz.almazbank.repositories.SberUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SberUserService implements UserDetailsService {
    @Autowired
    public SberUserRepository sberUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SberUser> sberUser = sberUserRepository.findByLogin(username);
        if (sberUser.isEmpty()){
            throw new UsernameNotFoundException(username);
        }else {
            SberUser sberUser1 = sberUser.get();
            return User.builder().username(sberUser1.getLogin()).password(sberUser1.getPassword()).build();
        }
    }
    public boolean existUser(Long id){
        return sberUserRepository.existsById(id);
    }
    public SberUser existByLogin(String login) throws UsernameNotFoundException{
        Optional<SberUser> user = sberUserRepository.findByLogin(login);
        if (user.isPresent()){
            return user.get();
        }else throw new UsernameNotFoundException(login);
    }
    public List<String> convertPhoneNumberToList(String phonenumbers){
        return Stream.of(phonenumbers.split("\\s*,\\s*"))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
