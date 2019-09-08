package com.locngo.loansystem.notificationsystem.mail.service;

import com.locngo.loansystem.notificationsystem.mail.model.Mail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MailService {

    void send(Mail mail);

    ResponseEntity<?> sendMail(Mail mail);
}
