package com.domain.tasks.tasks.scheduler;

import com.domain.tasks.tasks.config.AdminConfig;
import com.domain.tasks.tasks.domain.Mail;
import com.domain.tasks.tasks.repository.TaskRepository;
import com.domain.tasks.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduer {

    private static final String SUBJECT = "Tasks: Once a day email";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    //    @Scheduled(cron = "0 0 10 * * *")
    @Scheduled(fixedDelay = 10000)
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String message;
        if (size == 1) {
            message = "Currently in database you got: " + size + " task";
        } else {
            message = "Currently in database you got: " + size + " tasks";
        }
        simpleEmailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, message));
    }
}
