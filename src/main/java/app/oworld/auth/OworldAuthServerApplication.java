package app.oworld.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@SpringBootApplication
public class OworldAuthServerApplication {

    @GetMapping
    public String hello(){
        return "Hello There👋";
    }

    public static void main(String[] args) {
        SpringApplication.run(OworldAuthServerApplication.class, args);

    }

}
