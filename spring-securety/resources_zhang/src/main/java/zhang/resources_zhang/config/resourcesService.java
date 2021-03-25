package zhang.resources_zhang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableResourceServer
public class resourcesService extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID="rest1";
//    private String JWT_KEY="shunzi";

//    @Bean
//    public TokenStore tokenStore(){
//
//        return new JwtTokenStore(accessTokenConverter());
//    }
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter(){
//
//        JwtAccessTokenConverter converter=new JwtAccessTokenConverter();
//        converter.setSigningKey(JWT_KEY);
//        return converter;
//    }


//    @Bean
//    public RestTemplate RestTemplate(){
//
//        return new RestTemplate();
//    }

    @Bean
    public ResourceServerTokenServices ResourceServerTokenServices(){

        RemoteTokenServices service=new RemoteTokenServices();
        service.setCheckTokenEndpointUrl("http://localhost:9000/oauth/check_token");
        service.setClientId("c");
        service.setClientSecret("cc");
        return service;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)
                .tokenServices(ResourceServerTokenServices())
                .stateless(true);
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//无状态
    }
}
