package spring.springsecurety;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class SpringSecuretyApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringSecuretyApplication.class, args);
	}

}
