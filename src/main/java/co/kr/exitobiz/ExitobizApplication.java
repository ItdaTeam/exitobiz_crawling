package co.kr.exitobiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExitobizApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExitobizApplication.class, args);
	}

}
