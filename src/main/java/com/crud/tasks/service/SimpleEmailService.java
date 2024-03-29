package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class SimpleEmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailCreatorService mailCreatorService;

    final private static Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    public void send(final Mail mail) {
        LOGGER.info("starting email preparation...");
        try {
            //SimpleMailMessage mailMessage = createMailMessage(mail);
            //javaMailSender.send(mailMessage);
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("massage has been sent");
        } catch (MailException e) {
            LOGGER.error("failed to process mail sending: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail){
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        ofNullable(mail.getToCC()).ifPresent(mailMessage::setCc);
        return mailMessage;
    }

    public void sendDailyInfo(final Mail mail) {
        LOGGER.info("daily email preparation...");
        try {
            javaMailSender.send(createDailyReport(mail));
            LOGGER.info("massage has been sent");
        } catch (MailException e) {
            LOGGER.error("failed to process mail sending: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createDailyReport(final Mail mail){
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTasksQuaEmail(), true);
        };
    }
}
