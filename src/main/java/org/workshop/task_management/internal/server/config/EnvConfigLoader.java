package org.workshop.task_management.internal.server.config;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

public class EnvConfigLoader implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        // โหลดไฟล์ .env.local ก่อนที่ Spring Context จะเริ่มต้น
        Dotenv dotenv = Dotenv.configure()
                .filename(".env.local")
                .ignoreIfMissing()
                .load();

        // แคชชิงค่าลง System Environment (Optional)
        dotenv.entries().forEach(entry -> {
            if (System.getenv(entry.getKey()) == null) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });

    }
}
