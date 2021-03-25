package zhang.getway_zhang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PeiZhiLei {

    @Bean
    public RestTemplate RestTemplate(){

        return new RestTemplate();
    }
}
