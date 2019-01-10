package pack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[])
    {
        SpringApplication.run(Application.class);
        ApplicationContext factory = new AnnotationConfigApplicationContext(SpamWithNumbersConfig.class);
        //CommandLineRunner com = factory.getBean(CommandLineRunner.class); // what happens?
    }
}