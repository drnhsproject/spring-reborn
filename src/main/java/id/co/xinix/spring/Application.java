package id.co.xinix.spring;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@SpringBootApplication
@ComponentScan(basePackages = {
        "id.co.xinix.spring",
        "id.co.xinix.auth",
        "id.co.xinix.media"
})
@EntityScan(basePackages = {
        "id.co.xinix.auth.modules",
        "id.co.xinix.spring.modules",
        "id.co.xinix.media.modules"
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
