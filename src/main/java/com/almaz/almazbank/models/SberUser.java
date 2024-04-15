package com.almaz.almazbank.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sberUsers")
public class SberUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String FIO;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private String phoneNumbers;
    private String emails;
    private String password;
    @OneToOne(mappedBy = "sberUser", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SberAccount sberAccount;

    @Transient
    private List<String> phoneList;
    @Transient
    private List<String> emailList;

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<String> phoneList) {
        this.phoneList = phoneList;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public SberUser() {
    }

    public SberUser(String login, String FIO, Date birthday, String phoneNumbers, String emails, String password) {
        this.login = login;
        this.FIO = FIO;
        this.birthday = birthday;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
        this.password = password;
//        this.sberAccount = new SberAccount(initialBalance);
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getLogin() {
        return login;
    }

    public String getFIO() {
        return FIO;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getEmails() {
        return emails;
    }

    public SberAccount getSberAccount() {
        return sberAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
