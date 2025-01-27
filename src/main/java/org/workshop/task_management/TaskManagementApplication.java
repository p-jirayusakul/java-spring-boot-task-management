package org.workshop.task_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.workshop.task_management.internal.server.config.EnvConfigLoader;


@SpringBootApplication
public class TaskManagementApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TaskManagementApplication.class);
        app.addListeners(new EnvConfigLoader());
        app.run(args);

    }

}
