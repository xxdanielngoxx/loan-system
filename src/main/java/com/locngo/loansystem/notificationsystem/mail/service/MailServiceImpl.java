package com.locngo.loansystem.notificationsystem.mail.service;

import com.locngo.loansystem.notificationsystem.mail.model.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component("MailServiceImpl")
public class MailServiceImpl implements MailService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    private final TemplateEngine templateEngine;

    private final SendMailHandler sendMailHandler;

    public MailServiceImpl(TemplateEngine templateEngine,
                            SendMailHandler sendMailHandler) {
        this.templateEngine = templateEngine;
        this.sendMailHandler = sendMailHandler;
    }

    @Override
    public void send(Mail mail) {
        final Context context = new Context();
        context.setVariable("message", mail.getMessage());
        String body = templateEngine.process("email/email-template", context);
        sendMailHandler.sendPreparedMail(mail.getEmail(), mail.getObject(), body, true);
    }

    @Override
    public ResponseEntity<?> sendMail(Mail mail) {
        send(mail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
