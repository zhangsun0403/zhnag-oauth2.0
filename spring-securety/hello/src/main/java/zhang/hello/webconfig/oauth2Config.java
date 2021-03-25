package zhang.hello.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class oauth2Config {

    @Bean
    public RestTemplate RestTemplate(){

        return new RestTemplate();
    }
}
