package com.domain.tasks.tasks.service;

import com.domain.tasks.tasks.domain.Mail;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SimpleEmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void send(final Mail mail){
        log.info("Starting email preparation...");
        try {

            javaMailSender.send(createMailMessage(mail));

            log.info("Email has been sent.");
        }catch (MailException e){
            log.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail){
        val mailMessage = new SimpleMailMessage();
            mailMessage.setTo(mail.getMailTo());
            mailMessage.setSubject(mail.getSubject());
            mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
