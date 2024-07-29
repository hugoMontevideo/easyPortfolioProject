package com.simplon.easyportfolio.api.email;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailTestingController {

 private EmailService emailService;

    public EmailTestingController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send-test-mail")
  public String sentEmailTest(){
      System.out.println("hiii");
      emailService.sendEmail( "test@example.com", "Email testing from SpringBoot", "This a test Email");

      return "Email test sent successfully ";
  }

}
