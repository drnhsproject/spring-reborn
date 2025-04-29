package id.co.xinix.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@ComponentScan(basePackages = "id.co.xinix.spring")
@CommandScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
