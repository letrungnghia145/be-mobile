package com.nghiale.api.utils.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import com.nghiale.api.config.MailConfig;

public class AppMail {

	public static void send(String toPerson, String subject, String body) {
		System.out.println("Sending to " + toPerson + "....");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", MailConfig.HOST_NAME);
		properties.put("mail.smpt.port", MailConfig.SSL_PORT);
		properties.put("mail.smtp.socketFactory.port", MailConfig.SSL_PORT);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MailConfig.APP_EMAIL, MailConfig.APP_PASSWORD);
			}
		});

		MimeMessage message = new MimeMessage(session);
		try {
			message.setRecipients(MimeMessage.RecipientType.TO, toPerson);
			message.setSubject(subject);
			message.setContent(body + MailConfig.SIGNATURE, "text/html");

			Transport.send(message);
			System.out.println("Mail has sent to " + toPerson + "!");
		} catch (MessagingException e) {
			System.out.println("Couldn't send mail to " + toPerson + "!");
		} finally {

		}
	}

//	public static void main(String[] args) {
//		AppMail sMail = new RecoveryPasswordMail();
//		sMail.send("nghia1k45@gmail.com");
//		//		sMail.send(RecoveryPasswordMail.SUBJECT, sMail.getContent()+MailConfig.SIGNATURE);
//	}
////	public static void name() {
////		
////	}
//	public static void main(String[] args) {
//		AppMail.send("nghia1k45@gmail.com", MailAction.REGISTER_NEW_ACCOUNT.toString(),
//				MailConfig.createContent(MailAction.REGISTER_NEW_ACCOUNT));
//	}
}
