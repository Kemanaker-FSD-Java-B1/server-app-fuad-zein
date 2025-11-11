package com.project.serverapp.controller;

import com.project.serverapp.dto.request.AttachMailRequest;
import com.project.serverapp.dto.request.SimpleMailRequest;
import com.project.serverapp.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/email")
public class EmailController {

  private EmailService emailService;

  @PostMapping("/simple")
  public SimpleMailRequest sendSimpleMessage(
    @RequestBody SimpleMailRequest simpleMailRequest
  ) {
    return emailService.sendSimpleMessage(simpleMailRequest);
  }

  @PostMapping("/attach")
  public AttachMailRequest sendMessageWithAttachment(
    @RequestBody AttachMailRequest attachMailRequest
  ) {
    return emailService.sendMessageWithAttachment(attachMailRequest);
  }
}
