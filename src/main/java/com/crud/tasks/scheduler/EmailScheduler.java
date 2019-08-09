package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    SimpleEmailService simpleEmailService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: Once a day email";

    @Scheduled(fixedDelay = 1000000)
    public void sendInformationEmail(){
        long size = taskRepository.count();
        simpleEmailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                getMessage(size),
                null
        ));
    }

    private String getMessage(long size){
        return (size == 0) ? "There is no any task in the database" : new StringBuilder()
                .append("You've got ")
                .append(size)
                .append((size == 1) ? " task" : " tasks")
                .append(" in database")
                .toString();
    }
}
