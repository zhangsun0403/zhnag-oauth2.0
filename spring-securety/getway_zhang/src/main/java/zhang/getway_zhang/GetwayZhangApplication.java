package zhang.getway_zhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GetwayZhangApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetwayZhangApplication.class, args);
	}

}
