package com.almaz.almazbank.models;

import java.util.Date;

public record SberUserRecord (String login, String surname,String name, String middle_name, String birthday, String phoneNumbers, String emails, String password, String balanceOnSberAccount){
    @Override
    public String balanceOnSberAccount() {
        if (balanceOnSberAccount.length()==0 || Double.parseDouble(balanceOnSberAccount)<0){
            return "0";
        }
        else return balanceOnSberAccount;
    }
}
