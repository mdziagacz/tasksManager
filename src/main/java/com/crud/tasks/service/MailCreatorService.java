package com.crud.tasks.service;

import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    AdminConfig adminConfig;

    public String buildTrelloCardEmail(String message){
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
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
