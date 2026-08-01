package com.ecotrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendReminderEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + to);
        }
    }

    // Daily at 9am
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyReminders() {
        System.out.println("Sending daily carbon logging reminders...");
        // In a real app, query users who opted in and haven't logged today
        sendReminderEmail("user@example.com", "Log your Carbon Footprint!", "Don't forget to log your footprint today!");
    }
}
