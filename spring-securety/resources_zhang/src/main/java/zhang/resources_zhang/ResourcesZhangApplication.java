package zhang.resources_zhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ResourcesZhangApplication {


	public static void main(String[] args) {
		SpringApplication.run(ResourcesZhangApplication.class, args);
	}

}
