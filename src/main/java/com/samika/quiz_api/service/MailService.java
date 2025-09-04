package com.samika.quiz_api.service;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MailService {
    private static final Logger log = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender sender;
    public MailService(JavaMailSender sender){ this.sender = sender; }

    public void notifyUsers(List<String> emails, String subject, String body){
        try {
            if (emails==null || emails.isEmpty()) return;
            var msg = new SimpleMailMessage();
            msg.setTo(emails.toArray(String[]::new)); msg.setSubject(subject); msg.setText(body);
            sender.send(msg);
        } catch (Exception e) { log.warn("Email skipped (configure SMTP later): {}", e.getMessage()); }
    }
}
