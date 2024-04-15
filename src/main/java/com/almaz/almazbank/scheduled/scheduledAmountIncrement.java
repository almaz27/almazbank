package com.almaz.almazbank.scheduled;

import com.almaz.almazbank.services.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


import java.util.Date;


@Component
public class scheduledAmountIncrement {
    private final BankAccountService bankAccountService;
    private static final Logger log = LoggerFactory.getLogger(scheduledAmountIncrement.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public scheduledAmountIncrement(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }


//        @Scheduled(fixedRate = 60000)
        public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        double balance = bankAccountService.saveIncrementedBalance(2).getBalance();
        log.info("Balance get "+balance);
    }
}
