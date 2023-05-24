package springboot2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "the title",
                version = "1.0",
                description = "My API"
        ))
public class SprigBoot2Application {

    public static void main(String[] args) {
        SpringApplication.run(SprigBoot2Application.class, args);
    }

}
