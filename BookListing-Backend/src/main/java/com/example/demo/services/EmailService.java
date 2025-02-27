package com.example.demo.services;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

//    public void sendDownloadLink(String toEmail, String downloadLink) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(fromEmail);
//        message.setTo(toEmail);
//        message.setSubject("Your Book Download Link");
//        message.setText("Click the link below to download your book:\n" + downloadLink);
//        emailSender.send(message);
//    }

    public void sendDownloadLink(String toEmail, String downloadLink) throws MessagingException {
        // Create email context
        Context context = new Context();
        context.setVariable("downloadLink", downloadLink);

        // Process Thymeleaf template
        String emailContent = templateEngine.process("email-template", context);

        // Create MimeMessage
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("Your Book Download Link");
        helper.setText(emailContent, true); // Enable HTML format

        // Send email
        emailSender.send(message);
    }
}
