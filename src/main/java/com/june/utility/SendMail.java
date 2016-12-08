package com.june.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendMail{

	@Autowired
	private JavaMailSender  mailSender;

	public void setMailSender(JavaMailSender  mailSender) {
		this.mailSender = mailSender;
	}
	//发送普通文本邮件
	public void sendTextEmail(String emails,String subject,String text,String fromEmail)
	{
		//SimpleMailMessage只能用来发送text格式的邮件     
        SimpleMailMessage mail = new SimpleMailMessage();
        if (emails!=null && !emails.equals("")) {
	        String email[] = emails.split(";");   
	        for(int i=0;i<email.length;i++) {   
	                mail.setTo(email[i]);//接受者   
	                mail.setFrom(fromEmail);    
	                mail.setSubject(subject);//主题      
	                mail.setText(text);//邮件内容      
	                mailSender.send(mail);      
	        }  
        }
	}
	//发送html格式的邮件
	public void sendHtmlEmail(String emails,String subject,String text,String fromEmail) throws MessagingException
	{
		 MimeMessage msg = mailSender.createMimeMessage();
		 MimeMessageHelper helper = new MimeMessageHelper(msg, true, "GB2312");
		 if (emails!=null  && !emails.equals("")) {
			 String email[] = emails.split(";");   
		     for(int i=0;i<email.length;i++) {   
			 helper.setTo(email[i]);
			 helper.setFrom(fromEmail);
			 helper.setSubject(subject);
			 helper.setText(text, true);
			 mailSender.send(msg);
		     }
		}
		 
	}
}
