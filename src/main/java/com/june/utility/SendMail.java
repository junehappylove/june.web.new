package com.june.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 邮件发送 SendMail <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年1月19日 下午8:05:59
 * @version 1.0.0
 */
@Service
public class SendMail {

	@Autowired
	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 发送普通文本邮件
	 * @param emails 邮箱
	 * @param subject 主题
	 * @param text 正文
	 * @param fromEmail 接受者邮箱
	 * @date 2017年1月19日 下午8:06:18
	 * @writer junehappylove
	 */
	public void sendTextEmail(String emails, String subject, String text, String fromEmail) {
		// SimpleMailMessage只能用来发送text格式的邮件
		SimpleMailMessage mail = new SimpleMailMessage();
		if (emails != null && !emails.equals("")) {
			String email[] = emails.split(";");
			for (int i = 0; i < email.length; i++) {
				mail.setTo(email[i]);// 接受者
				mail.setFrom(fromEmail);
				mail.setSubject(subject);// 主题
				mail.setText(text);// 邮件内容
				mailSender.send(mail);
			}
		}
	}

	/**
	 * 发送html格式的邮件
	 * @param emails 发送者邮箱
	 * @param subject 主题
	 * @param text 正文
	 * @param fromEmail 接受者邮箱
	 * @throws MessagingException
	 * @date 2017年1月19日 下午8:06:25
	 * @writer junehappylove
	 */
	public void sendHtmlEmail(String emails, String subject, String text, String fromEmail) throws MessagingException {
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "GB2312");
		if (emails != null && !emails.equals("")) {
			String email[] = emails.split(";");
			for (int i = 0; i < email.length; i++) {
				helper.setTo(email[i]);
				helper.setFrom(fromEmail);
				helper.setSubject(subject);
				helper.setText(text, true);
				mailSender.send(msg);
			}
		}

	}
}
