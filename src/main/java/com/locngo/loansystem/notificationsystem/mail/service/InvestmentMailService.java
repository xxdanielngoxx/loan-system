package com.locngo.loansystem.notificationsystem.mail.service;

import com.locngo.loansystem.notificationsystem.mail.model.Mail;
import org.springframework.http.ResponseEntity;

public class InvestmentMailService implements MailService {

    @Override
    public void send(Mail mail) {

    }

    @Override
    public ResponseEntity<?> sendMail(Mail mail) {
        return null;
    }
}
