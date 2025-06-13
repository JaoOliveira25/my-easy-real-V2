package utils;

import java.io.InputStream;
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
	
	
	
	private static String username ;
	private static String password ;
	
	private String listaDestinatarios = "";
	private String remetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	
	static {
		try {
			Properties properties = new Properties();
			InputStream input = EnviaEmail.class.getClassLoader().getResourceAsStream("config.properties");			
			
			if(input == null) {
				throw new RuntimeException("Arquivo config.properties não encontrado no classpath.");
			}
			
			properties.load(input);
			
			username = properties.getProperty("email.username");
			password = properties.getProperty("email.password");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao carregar dados de e-mail: " + e.getMessage());
		}
	}
	
	
	

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
