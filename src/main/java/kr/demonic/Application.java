package kr.demonic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// DB연결이 결정되지 않았다면 아래 주석 해제.
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "kr.demonic")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
