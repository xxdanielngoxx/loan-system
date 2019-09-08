package com.locngo.loansystem.notificationsystem.mail.controller;

import com.locngo.loansystem.notificationsystem.mail.model.Mail;
import com.locngo.loansystem.notificationsystem.mail.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/services/send-email")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/test")
    public ResponseEntity sendEmail(@Valid @RequestBody Mail mail, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        return mailService.sendMail(mail);
    }
}
