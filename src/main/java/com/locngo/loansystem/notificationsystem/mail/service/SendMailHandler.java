package com.locngo.loansystem.notificationsystem.mail.service;

import com.locngo.loansystem.errorhandling.error.NotificationInterruptException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SendMailHandler {

    private JavaMailSender javaMailSender;

    public SendMailHandler(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public Boolean sendPreparedMail(String to, String subject, String text, Boolean isHtml) {
        try {
            MimeMessage mail = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            javaMailSender.send(mail);
            return true;
        } catch (MessagingException e) {
            throw new NotificationInterruptException("Error occurred when sending email!");
        }
    }
}
