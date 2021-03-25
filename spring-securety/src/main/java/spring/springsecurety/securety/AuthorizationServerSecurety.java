package spring.springsecurety.securety;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import spring.springsecurety.userdetilService.UserService;


import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerSecurety extends AuthorizationServerConfigurerAdapter {

    @Resource
    private PasswordEncoder PasswordEncoder;

    @Resource
    private UserService userService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private TokenStore tokenStore;

    @Resource
    private ClientDetailsService ClientDetailsService;


//    @Autowired
//    private JwtAccessTokenConverter accessTokenConverter;



    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();//表单认证
    }

    /**
     * 客户端详情服务
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //客户端详情服务
        clients.inMemory()
                .withClient("c")
                .secret(PasswordEncoder.encode("cc"))
                .resourceIds("rest1")
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")
                .scopes("all")
                .autoApprove(false)//跳转到授权页面，true不跳转页面
                .redirectUris("http://www.baidu.com");


    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.authenticationManager(authenticationManager)
                .authorizationCodeServices(AuthorizationCodeServices())
                .tokenServices(tokenServices())
//                .userDetailsService(userService)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);


    }

    /**
     * 令牌服务
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices service=new DefaultTokenServices();
        service.setClientDetailsService(ClientDetailsService);
        service.setSupportRefreshToken(true);
        service.setTokenStore(tokenStore);
//        // 令牌增强
//        TokenEnhancerChain tokenEnhancerChain=new TokenEnhancerChain();
//        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
//        service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(20000); //token有效期
        service.setRefreshTokenValiditySeconds(70000); //token刷新时间间隔


        return service;
    }

    /**
     * //授权码服务
     * @return
     */
    @Bean
    public AuthorizationCodeServices AuthorizationCodeServices(){

        return new InMemoryAuthorizationCodeServices();
    }

}
