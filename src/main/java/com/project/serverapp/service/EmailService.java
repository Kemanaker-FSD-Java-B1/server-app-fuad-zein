package com.project.serverapp.service;

import com.project.serverapp.dto.request.AttachMailRequest;
import com.project.serverapp.dto.request.SimpleMailRequest;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EmailService {

  private JavaMailSender javaMailSender;

  public SimpleMailRequest sendSimpleMessage(
    SimpleMailRequest simpleMailRequest
  ) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(simpleMailRequest.getTo());
    message.setSubject(simpleMailRequest.getSubject());
    message.setText(simpleMailRequest.getBody());

    javaMailSender.send(message);

    log.info("Email send to : {}", simpleMailRequest.getTo());

    return simpleMailRequest;
  }

  @SuppressWarnings("null")
  public AttachMailRequest sendMessageWithAttachment(
    AttachMailRequest attachMailRequest
  ) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();

      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom("noreply@baeldung.com");
      helper.setTo(attachMailRequest.getTo());
      helper.setSubject(attachMailRequest.getSubject());
      helper.setText(attachMailRequest.getBody());

      FileSystemResource file = new FileSystemResource(
        new File(attachMailRequest.getAttachment())
      );
      helper.addAttachment(file.getFilename(), file);

      log.info("Email to = {}", attachMailRequest.getTo());
      javaMailSender.send(message);
    } catch (Exception e) {
      log.error("Email to = {}", attachMailRequest.getTo());
      System.out.println("Message = " + e.getMessage());
    }
    return attachMailRequest;
  }
}
