package com.challenge.alkemy.api.disney.mailclient;


import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderWelcomeService {

	private final static Logger logger = LoggerFactory.getLogger(EmailSenderWelcomeService.class);
	
	private final static String PATH_MAIL_IMAGE = "src/main/resources/static/images/imageMail.png";


	@Autowired
    private JavaMailSender mailSender;

	@Value( "${spring.mail.username}" )
	private String mailFrom;
	

	public void sendSimpleEmailWithImage(String toEmail,
	        String subject) throws MessagingException
	{
		try {
			File attachment = new File(PATH_MAIL_IMAGE);
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(mailFrom);
			helper.setTo(toEmail);
			helper.setSubject(subject);

			helper.setText(
		            "<html>"
		            + "<body>"
		             + "<div>Registracion exitosa,"
		                + "<div>"
		                + "<img src='cid:rightSideImage'/>"
		                + "<div>Gracias por registrarse en nuestra api.</div>"
		                + "<div>Espero que sea de mucha utilidad para usted.</div>"
		                + "</div>"
		                + "<div>Muchas gracias,</div>"
		                + "Api Rest Disney"
		              + "</div></body>"
		            + "</html>", true);

			// attach the file into email body
			helper.addInline("rightSideImage", attachment );

			mailSender.send(mimeMessage);

	        logger.info(String.format("Enviando mail de (%s) a (%s)", mailFrom, toEmail));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


