package com.inalab.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/***
 * Emailer class is an utility class for sending emails with ot without
 * attachments using the SMTP protocol
 * 
 */
public class Email extends Thread implements Serializable {

	private static final Log log = LogFactory.getLog(Email.class);
	public static final String SMTP_SERVER = PropertiesUtil.getProperty("SMTP.SERVER", "mail.livementer.com");
	public static final String EMAIL_FROM = PropertiesUtil.getProperty("EMAIL.FROM",
			"no-reply-isociallabs@livementer.com");
	private static final String SUBJECT = PropertiesUtil.getProperty("EMAIL.SUBJECT", "Twitter Mention Alert");
	private static long ctr;
	private Map timerMap = new TreeMap();
	private static Properties props = System.getProperties();

	private String emailTo;
	private String message;
	private String subject;
	private String from;
	private String emailBcc;

	private String attachmentFilename;

	private String contentType;
	private boolean debug = Boolean.valueOf(PropertiesUtil.getProperty("SMTP.DEBUG", "true")).booleanValue();
	private static Logger logger = Logger.getLogger(Email.class);

	public Email(String emailTo, String message) {
		this(null, emailTo, null, message, "text/plain");
	}

	public Email(String emailTo, String message, String contentType) {
		this(null, emailTo, null, message, contentType);
	}

	public Email(String from, String emailTo, String subject, String message, String contentType) {
		this(from, emailTo, subject, message, contentType, null);
	}

	public Email(String from, String emailTo, String subject, String message, String contentType,
			String attachmentFilename) {

		this(from, emailTo, subject, message, contentType, attachmentFilename, null);
	}

	/**
	 * Send an email with attachment
	 * 
	 * @param from
	 * @param emailTo
	 * @param subject
	 * @param message
	 * @param contentType
	 * @param attachmentFilename
	 *            : if not null, then attach file
	 */
	public Email(String from, String emailTo, String subject, String message, String contentType,
			String attachmentFilename, String bccEmailAddr) {
		super("EmailThread-" + (++ctr));
		System.setProperty("mail.host", SMTP_SERVER); // Set based upon property

		System.setProperty("mail.smtp.port", "26");
		// System.setProperty("mail.smtp.submitter", "livementer.com");
		// Authenticator authenticator = new Authenticator();
		// System.setProperty("mail.smtp.submitter",
		// authenticator.getPasswordAuthentication().getUserName());
		System.setProperty("mail.smtp.auth", "true");
		if (from == null)
			this.from = EMAIL_FROM;
		else
			this.from = from;
		this.emailTo = emailTo;
		this.message = message;
		if (subject == null)
			this.subject = SUBJECT;
		else
			this.subject = subject;

		this.emailBcc = bccEmailAddr;

		this.contentType = contentType;

		this.attachmentFilename = attachmentFilename;

		logger.debug("Message " + message);

		log.debug("Message " + message);
	}

	public void mail() throws Exception {
		timerMap.put("Emailer.mail", new Long(System.currentTimeMillis()));

		if ("Y".equals(PropertiesUtil.getProperty("SMTP.SERVER.ENABLED", "Y"))) {
			Session session = getSession();
			session.setDebug(debug);

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, false));

			if (!StringUtils.isEmpty(this.emailBcc)) {
				logger.warn("Processing Email inside[" + this.emailBcc);

				InternetAddress[] ia = InternetAddress.parse(this.emailBcc);

				if (!StringUtils.isEmpty(this.emailBcc)) {
					msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(this.emailBcc, false));
				}

			}

			msg.setSubject(subject);
			msg.setText(message);
			msg.setHeader("X-Mailer", "JavaMail");
			msg.setHeader("Content-type", contentType);
			msg.setSentDate(new Date());

			if (attachmentFilename != null && attachmentFilename.trim().length() > 0) {
				// Create the message part
				BodyPart messageBodyPart = new MimeBodyPart();

				// Fill the message
				messageBodyPart.setText(message);

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);

				// Part two is attachment
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachmentFilename);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(attachmentFilename);
				multipart.addBodyPart(messageBodyPart);

				// Put parts in message
				msg.setContent(multipart);
			}

			logger.warn("Transport.send");
			Transport.send(msg);
		}

		logger.warn("TIMING - Emailer.mail ["
				+ (System.currentTimeMillis() - ((Long) timerMap.get("Emailer.mail")).longValue()) + "] ms");

	}

	public void send() {
		start();
	}

	public void run() {
		try {
			mail();
		} catch (Exception e) {
			logger.error("Emailer error: " + e.getMessage() + "error sending email to: " + emailTo);
			logger.error("Email Error ", e);
		}
	}

	private Session getSession() {
		// Session session = Session.getDefaultInstance(props, null);

		// Setup mail server
		Authenticator auth = new PopupAuthenticator();
		// Get session
		Session session = Session.getInstance(props, auth);

		session.setDebug(false);

		return session;
	}

	/**
	 * Gets the emailTo
	 * 
	 * @return Returns a String
	 */
	public String getEmailTo() {
		return emailTo;
	}

	/**
	 * Sets the emailTo
	 * 
	 * @param emailTo
	 *            The emailTo to set
	 */
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	/**
	 * Gets the from
	 * 
	 * @return Returns a String
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Sets the from
	 * 
	 * @param from
	 *            The from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Gets the message
	 * 
	 * @return Returns a String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message
	 * 
	 * @param message
	 *            The message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the subject
	 * 
	 * @return Returns a String
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject
	 * 
	 * @param subject
	 *            The subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the contentType
	 * 
	 * @return Returns a String
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the contentType
	 * 
	 * @param contentType
	 *            The contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	static class PopupAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("no-reply-isociallabs@livementer.com", "2342Sdsf");
		}
	}

	/**
	 * determines whether the address is valid. Always check for the presence of
	 * an address and if a list of domains is found in the
	 * "ody.common.validEmailAddress" property then only addresses that send to
	 * those domains will be declared valid. This is used during test to insure
	 * that mails are only sent to internal addresses.
	 * 
	 * @param address
	 *            the email address being checked
	 * @return <code>true</code> if the address is valid. <code>false</code> if
	 *         the addrees is invalid or filtered out.
	 */
	public boolean validAddress(String address) {
		if (emailTo == null || emailTo.length() == 0) {
			return false;
		}

		if ("none".equalsIgnoreCase(emailTo)) {
			logger.debug("Emailer invalid address : <" + emailTo + ">");
			return false;
		}

		String validList = System.getProperty("ody.common.validEmailAddress", "ALL");

		if ("ALL".equalsIgnoreCase(validList) || "".equals(validList)) {
			return true;
		}

		// is the end of the address in the approved list??
		return (validList.indexOf(address.substring(address.indexOf("@") + 1)) > -1);
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return debug;
	}

	public static void main(String[] args) {
		Email email = new Email("JigHShah@gmail.com", "testing email");
		email.run();
	}

}
