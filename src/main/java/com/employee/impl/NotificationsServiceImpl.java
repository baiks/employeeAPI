package com.employee.impl;

import com.employee.configs.NotificationConfig;
import com.employee.dtos.NotificationDto;
import com.employee.services.NotificationsService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Properties;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationsServiceImpl implements NotificationsService {
    private final NotificationConfig mailConfig;

    /**
     * @param notificationDto
     */
    @Override
    public void sendEmail(NotificationDto notificationDto) {
        // SMTP Properties
        Properties prop = new Properties();
        prop.put("mail.smtp.host", mailConfig.getSmtp().getHost());
        prop.put("mail.smtp.port", mailConfig.getSmtp().getPort());
        prop.put("mail.smtp.auth", mailConfig.getSmtp().isAuth());
        prop.put("mail.smtp.starttls.enable", mailConfig.getSmtp().getStarttls().isEnable());

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailConfig.getUsername()));

            if (notificationDto.getRecipients().size() == 1) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notificationDto.getRecipients().stream().findFirst().get()));
            } else {
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(String.join(",", notificationDto.getRecipients()))
                );
            }
            message.setSubject(notificationDto.getSubject());
            message.setText(notificationDto.getContent());

            // Send the email
            Transport.send(message);

            log.info("Email sent successfully to {}", notificationDto.getRecipients());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
