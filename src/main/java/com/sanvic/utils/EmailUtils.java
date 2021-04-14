package com.sanvic.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender;
	
	public Boolean sendAccRegMail(String subject, String body, String to)
	{
		Boolean isSent = false;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
		helper.setSubject(subject);
		helper.setTo(to);
		 
		boolean isHtmlTxt = true;
		helper.setText(body, isHtmlTxt);
		
		mailSender.send(message);
		isSent = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return isSent;
	}
	
}
