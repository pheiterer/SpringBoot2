package springboot2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition (info =
@Info(
        title = "Spring Boot Course",
        version = "999999999999999999.0",
        description = "My First API",
        license = @License(name = "Apache 2.0", url = "http://foo.bar"),
        contact = @Contact(url = "https://github.com/RydelMorgan", name = "Paulo Henrique", email = "pheiterer@hotmail.com")
)
)
public class SprigBoot2Application {

    public static void main(String[] args) {
        SpringApplication.run(SprigBoot2Application.class, args);
    }

}
