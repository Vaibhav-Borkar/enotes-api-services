package com.enotes.config;

import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailService {

	private final JavaMailSender sender;
	
	public EmailService (JavaMailSender sender) {
		this.sender=sender;
	}
	
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	public void sendEmail(EmailRequest req) throws UnsupportedEncodingException, MessagingException {
		log.info("EmailService : sendEmail() : Start");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(mailFrom,req.getTitle());
		helper.setTo(req.getTo());
		helper.setSubject(req.getSubject());
		helper.setText(req.getMessage(),true);
		log.info("EmailService : sendEmail() : Mail message pass in send method successfully");
		sender.send(message);
		log.info("EmailService : sendEmail() : End");
	}
}
