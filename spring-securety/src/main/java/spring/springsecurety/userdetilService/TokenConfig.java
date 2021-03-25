package spring.springsecurety.userdetilService;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TokenConfig {


//    private String JWT_KEY="shunzi";

    /**
     * 授权码存储服务
     * @return
     */
    @Bean
    public TokenStore tokenStore(){

        return new InMemoryTokenStore();
    }
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter(){
//
//        JwtAccessTokenConverter converter=new JwtAccessTokenConverter();
//        converter.setSigningKey(JWT_KEY);
//        return converter;
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public RestTemplate RestTemplate(){
//        RestTemplate restTemplate=new RestTemplate();
//
//
//        return restTemplate;
//    }


}
