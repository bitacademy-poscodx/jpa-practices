package ex02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ex02.domain"})
public class Ch02Ex02Application {
    public static void main(String[] args) {
        SpringApplication.run(Ch02Ex02Application.class, args);
    }
}