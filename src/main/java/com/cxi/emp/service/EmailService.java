package com.cxi.emp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService{
  @Autowired
  private JavaMailSender mailSender;

  public void sendVerificationEmail(String to, String verificationLink){
    try{
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(to);
      helper.setSubject("Verify your email");
      helper.setText("Click the following link to verify your email: <a href='" + verificationLink + "'>Verify Email</a>", true);
      mailSender.send(message);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to send email");
    }
  }
}
