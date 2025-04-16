package com.enotes.config;

import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

	private final JavaMailSender sender;
	
	public EmailService (JavaMailSender sender) {
		this.sender=sender;
	}
	
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	public void sendEmail(EmailRequest req) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(mailFrom,req.getTitle());
		helper.setTo(req.getTo());
		helper.setSubject(req.getSubject());
		helper.setText(req.getMessage(),true);
		
		sender.send(message);
		
	}
}
