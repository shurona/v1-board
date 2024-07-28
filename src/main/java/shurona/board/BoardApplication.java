package shurona.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(BoardApplication.class, args);
		String[] beanDefinitionNames = run.getBeanDefinitionNames();

	}

}
