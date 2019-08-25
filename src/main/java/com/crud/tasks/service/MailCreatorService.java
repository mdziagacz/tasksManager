package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    AdminConfig adminConfig;

    @Autowired
    DbService dbService;

    public String buildTrelloCardEmail(String message){
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage taksk");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://mohhio.github.io");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("company_goal", adminConfig.getCompanyGoal());
        context.setVariable("company_email", adminConfig.getAdminMail());
        context.setVariable("company_phone", adminConfig.getCompanyPhone());
        context.setVariable("goodbye", "Kind regards");
        context.setVariable("preview", "new card");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildTasksQuaEmail(){
        List<Task> tasks = dbService.getAllTasks();
        int taskQua = tasks.size();

        Context context = new Context();
        context.setVariable("preview", "daily info");
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("tasks_qua", taskQua);
        context.setVariable("tasks_list", tasks);
        context.setVariable("show_button", true);
        context.setVariable("tasks_url", "https://mohhio.github.io");
        context.setVariable("button", "Manage tasks");
        context.setVariable("goodbye", "Have a nice day!");
        return templateEngine.process("mail/tasks-qua-info-mail", context);
    }
}
