package challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Dribbble {
 
    public static void main(String[] args) {
        SpringApplication.run(Dribbble.class, args);
    }
}