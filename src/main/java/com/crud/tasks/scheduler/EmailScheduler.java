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
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: Once a day email";

    @Scheduled(cron = "0 0 6 * * MON-FRI")
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

    @Scheduled(cron = "0 0 6 * * MON-FRI")
    public void sendDailyTasksReport() {
        simpleEmailService.sendDailyInfo(new Mail(
                adminConfig.getAdminMail(),
                "Daily report",
                "",
                null
        ));
    }
}
