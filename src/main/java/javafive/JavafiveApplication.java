package javafive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JavafiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavafiveApplication.class, args);
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        String rawPassword = "pass123";
	        String encodedPassword = encoder.encode(rawPassword);
	        System.out.println("BCrypt password: " + encodedPassword);
	}

}
