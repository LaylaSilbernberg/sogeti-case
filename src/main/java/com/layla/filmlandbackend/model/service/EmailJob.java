package com.layla.filmlandbackend.model.service;

import com.layla.filmlandbackend.enums.SubscriptionCategory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailJob implements Job {

    @Value("spring.mail.username")
    private String from;
    private static final String SUBJECT = "Filmland Invoice";
    private JavaMailSender emailSender;

    public EmailJob(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public EmailJob() {
    }

    String[] username;
    String invoice;

    public void setUsername(String... username) {
        this.username = username;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(username);
        message.setSubject(SUBJECT);
        message.setSubject(invoice);
        emailSender.send(message);
    }
}
