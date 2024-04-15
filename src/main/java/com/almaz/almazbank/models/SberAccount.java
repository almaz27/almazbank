package com.almaz.almazbank.models;

import jakarta.persistence.*;
import com.almaz.almazbank.services.incrementByTenPercent;

@Entity
public class SberAccount implements incrementByTenPercent{
    @Id
    @Column(name = "sberUser_id")
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "sberUser_id")
    private SberUser sberUser;

    private double balance;

    public SberAccount(SberUser sberUser, double balance) {
        this.sberUser=sberUser;
        this.balance = balance;
    }

    public SberAccount() {
    }

    @Override
    public double increment() {
        double tenPercent = (double) (balance/100)*10;
        return balance+=tenPercent;
    }
}
