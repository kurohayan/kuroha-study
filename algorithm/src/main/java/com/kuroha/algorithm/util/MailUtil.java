package com.kuroha.algorithm.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author kuroha
 * @date 2019-04-02 15:30:33
 */
public class MailUtil {

	private static final String NICKNAME = "";
	private static final String SERVER = "master";
	/**
	 * 过段时间改为传入Properties
	 */

	private static final String HOST = "smtp.exmail.qq.com";
	private static final int PORT = 465;
	private static final String PROTOCOL = "smtps";
	private static final String ENCODING = "UTF-8";
	private static final boolean SSL_ON_CONNECT = true;
	private static final boolean DEBUG = false;
	private static final String ERROR_USERNAME = "";
	private static final String ERROR_PASSWORD = "";
	private static final String ERROR_NAME = "";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";
	private static final String NAME = "";
	private static final String[] RECIPIENTS = new String[]{""};
	private static final Properties properties = new Properties();

	private MailUtil() {
	}

	/**
	 * @param environment 当前环境
	 * @param subject     1 严重错误;2 普通错误;3 非预期结果;
	 * @param msg
	 * @param type
	 */
	public static void sendException(String environment,String name, final String subject, final String msg, final int type) {
		sendMail(environment, name, subject, msg, type);
	}

	/**
	 * @param subject     1 严重错误;2 普通错误;3 非预期结果;
	 * @param msg
	 * @param type
	 */
	public static void sendException(final String subject, final String msg, final int type) {
		String environment = "develop";
		String name = "工具类";
		sendMail(environment,name, subject, msg, type);
	}

	private static void sendMail(String environment, String name, String subject, String msg, int type) {
		StringBuilder builder = new StringBuilder("维权骑士-");
		builder.append(name);
		if (SERVER.equals(environment)) {
			builder.append("-生产");
		} else {
			builder.append("-测试");
		}

		builder.append("-系统警报-");
		switch (type) {
			case 1:
				builder.append("严重错误");
				break;
			case 2:
				builder.append("普通错误");
				break;
			case 3:
				builder.append("非预期结果");
				break;
			default:
				break;
		}
		builder.append(subject);
		SimpleEmail email = new SimpleEmail();
		email.setDebug(DEBUG);
		email.setSSLOnConnect(SSL_ON_CONNECT);
		email.setHostName(HOST);
		email.setSmtpPort(PORT);
		email.setAuthenticator(new DefaultAuthenticator(ERROR_USERNAME, ERROR_PASSWORD));
		try {
			email.setFrom(ERROR_USERNAME, ERROR_NAME);
			email.addTo(RECIPIENTS);
			email.setCharset(ENCODING);
			email.setSubject(builder.toString());
			email.setMsg(msg);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public static void send(String receiver, String title, String content) {
		SimpleEmail email = new SimpleEmail();
		email.setDebug(DEBUG);
		email.setSSLOnConnect(SSL_ON_CONNECT);
		email.setHostName(HOST);
		email.setSmtpPort(PORT);

		email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
		try {
			email.setFrom(USERNAME,NAME);
			email.addTo(receiver);
			email.setCharset("UTF-8");
			email.setSubject(title);
			email.setMsg(content);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单附件邮件发送
	 *
	 * @return String
	 * @author SunCheng 2016年10月21日 上午11:55:13
	 */
	public static String sendEmail(String receiver, String subject, String htmlText, String attachSource) {
		List<String> receivers = new ArrayList<>();
		receivers.add(receiver);
		return sendEmail(receivers, null, subject, htmlText, attachSource);
	}

	/**
	 * 发送邮件
	 *
	 * @param receivers
	 * @param blindCarbonCopys
	 * @param subject
	 * @param htmlText
	 * @param attachSource
	 * @return
	 */
	private static String sendEmail(List<String> receivers, List<String> blindCarbonCopys, String subject, String htmlText, String attachSource) {
		// 邮件session
		Session mailSession = getMailSession();

		// 设置邮件属性
		MimeMessage message = new MimeMessage(mailSession);
		try {
			// 主题
			message.setSubject(subject);
			String nick = MimeUtility.encodeText(NICKNAME);
			// 发件人
			message.setFrom(new InternetAddress((nick + " <" + USERNAME + ">")));
			// 收件人
			if (receivers != null && receivers.size() > 0) {
				InternetAddress[] sendTo = new InternetAddress[receivers.size()];
				for (int i = 0; i < receivers.size(); i++) {
					sendTo[i] = new InternetAddress(receivers.get(i));
				}
				message.addRecipients(Message.RecipientType.TO, sendTo);
			}
			// 抄送人
			if (blindCarbonCopys != null && blindCarbonCopys.size() > 0) {
				InternetAddress[] sendTo = new InternetAddress[blindCarbonCopys.size()];
				for (int i = 0; i < blindCarbonCopys.size(); i++) {
					sendTo[i] = new InternetAddress(blindCarbonCopys.get(i));
				}
				message.addRecipients(Message.RecipientType.CC, sendTo);
			}
			message.setContent(htmlText, "text/html;charset=UTF-8");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "设置主题、发件人、收件人错误";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "设置昵称错误";
		}


		// 邮件正文内容，包括html+图片
		MimeMultipart multipart = new MimeMultipart("related");

		// 邮件html
		MimeBodyPart text = new MimeBodyPart();
		try {
			text.setContent(htmlText, "text/html;charset=UTF-8");
			// add it
			multipart.addBodyPart(text);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "添加html内容错误";
		}


		// 整个邮件默认无附件
		MimeMultipart all = multipart;

		// 有附件的话
		if (!StringUtils.isBlank(attachSource)) {
			File file = new File(attachSource);
			if (file.exists()) {

				all = new MimeMultipart();

				// 正文内容作为整个邮件的部分
				MimeBodyPart body = new MimeBodyPart();
				try {
					body.setContent(multipart);
				} catch (MessagingException e) {
					e.printStackTrace();
					return "正文内容转为部分出错";
				}

				try {
					// 设置正文与附件之间的关系
					all.setSubType("mixed");
					all.addBodyPart(body);
				} catch (MessagingException e) {
					e.printStackTrace();
					return "添加全内容的正文部分出错";
				}

				// 添加附件
				MimeBodyPart attach = new MimeBodyPart();
				DataHandler dh1 = new DataHandler(new FileDataSource(attachSource));
				try {
					attach.setDataHandler(dh1);
					String filename = dh1.getName();
					//处理附件字，防止中文乱码问题
					attach.setFileName(MimeUtility.encodeText(filename));
					// add
					all.addBodyPart(attach);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return "处理附件字出错";
				} catch (MessagingException e) {
					e.printStackTrace();
					return "添加附件出错";
				}
			}
		}

		// put everything together
		try {
			message.setContent(all);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "整合全内容出错";
		}

		// 连接主机并发送
		try {
			Transport transport = mailSession.getTransport();
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return "连接出错";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "发送出错";
		}
		return "success";
	}

	/**
	 * 获取邮件session
	 */
	private static Session getMailSession() {
		// JavaMail需要Properties来创建一个session对象
		/*
		 * 在 JavaMail 中，可以通过 extends Authenticator 抽象类，在子类中覆盖父类中的
		 * getPasswordAuthentication() 方法，就可以实现以不同的方式来进行登录邮箱时的用户身份认证。JavaMail
		 * 中的这种设计是使用了策略模式（Strategy
		 */
		properties.put("mail.debug",false);
		properties.put("mail.event.executor",ThreadUtil.getExecutor());
		Session mailSession = Session.getDefaultInstance(properties, new Authenticator() {
			// 身份验证
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});
		mailSession.setDebug(true);

		return mailSession;
	}


	public static void sendHtmlEmail(String receiver, String title, String content) {
		Runnable runnable = () -> {
			System.setProperty("java.net.preferIPv4Stack", "true");
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setHostName(HOST);
			email.setSmtpPort(PORT);
			email.setSSLOnConnect(SSL_ON_CONNECT);
			email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
			try {
				email.setFrom(USERNAME, "维权骑士");
				email.addTo(receiver);
				email.setSubject(title);
				email.setCharset(ENCODING);
				email.setHtmlMsg(content);
				email.send();
			} catch (EmailException e) {
				e.printStackTrace();
			}
		};
		ThreadUtil.execute(runnable);
	}

	public static void sendCodeWarnEmail(String environment,final String subject, final String msg, int type){
		String sub = "维权骑士：";
		if (SERVER.equals(environment)) {
			sub += "生产";
		}else {
			sub += "测试";
		}
		sub += "系统警报";
		if(1 == type){
			sub += "严重错误";
		}else  if(2 == type){
			sub += "普通错误";
		}else  if(3 == type){
			sub += "非预期结果";
		}
		sub += subject;
		final String tsub = sub;
		sendDevEmail(RECIPIENTS, tsub, msg);
	}

	public static void sendDevEmail(String[] receivers, String subject, String msg){
		System.setProperty("java.net.preferIPv4Stack", "true");
		SimpleEmail email = new SimpleEmail();
		email.setSSLOnConnect(SSL_ON_CONNECT);
		email.setDebug(DEBUG);
		email.setHostName(HOST);
		email.setSmtpPort(PORT);
		email.setAuthenticator(new DefaultAuthenticator(ERROR_USERNAME, ERROR_PASSWORD));
		try {
			email.setFrom(ERROR_USERNAME, ERROR_NAME);
			email.addTo(receivers);
			email.setCharset(ENCODING);
			email.setSubject(subject);
			email.setMsg(msg);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
