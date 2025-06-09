package utils;

import java.util.Properties;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EnviaEmail {

	private String username = "joao.devcontatosuporte@gmail.com";
	private String password = "upvqluvqjhtrielz";
	private String listaDestinatarios = "";
	private String remetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public EnviaEmail(String listaDestinatarios, String remetente, String assuntoEmail, String textoEmail) {

		this.listaDestinatarios = listaDestinatarios;
		this.remetente = remetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail(boolean envioHtml) {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Address[] toUser = InternetAddress.parse(listaDestinatarios);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username, remetente));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assuntoEmail);

			if (envioHtml) {
				message.setContent(textoEmail, "text/html; charset=utf-8");
			} else {
				message.setText(textoEmail);
			}

			Transport.send(message);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
