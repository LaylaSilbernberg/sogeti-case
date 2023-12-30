package com.layla.filmlandbackend.model.service;

import com.layla.filmlandbackend.enums.SubscriptionCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EmailService{

    @Value("spring.mail.username")
    private String from;
    private static final String SUBJECT = "Filmland Invoice";

    private JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMail(String username, SubscriptionCategory category){
        String invoice = """
                To whom it may concern,
                We would like to inform you that your payment for the
                %s Filmland subscription is due. Please pay the agreed upon
                sum within 30 days. On behalf of Filmland, we are thrilled
                to have you with us and we hope you continue enjoy quality
                cinema and series.""".formatted(category.getName());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(username);
        message.setSubject(SUBJECT);
        message.setSubject(invoice);
        emailSender.send(message);
    }
}
