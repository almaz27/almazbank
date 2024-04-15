package com.almaz.almazbank.models;


import jakarta.persistence.*;
import com.almaz.almazbank.services.*;

@Entity
@Table(name = "bankAccount")
public class BankAccount implements incrementByTenPercent{
    @Id
    @Column(name = "user_id")
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private MyUser user;
    @Column(name = "balance")
    private double balance;

    public BankAccount() {
    }

    public BankAccount(MyUser user) {
        this.user = user;
        this.balance = 0.0;
    }

    public double getBalance() {
        return balance;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }
    public double addBalance(double amount){
        return balance+=amount;
    }
    public boolean withdraw(double amount){
        if(balance>amount){
            balance-=amount;
            return true;
        }else return false;
    }


    @Override
    public double increment() {
        double tenPercent = (double) (balance/100)*10;
        return balance+=tenPercent;
    }
}