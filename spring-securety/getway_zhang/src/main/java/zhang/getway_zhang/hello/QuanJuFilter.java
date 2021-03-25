package zhang.getway_zhang.hello;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 *  http://localhost:9000/oauth/token 授权地址
 *
 */
@Service
public class QuanJuFilter implements GlobalFilter {



    @Autowired
    private RestTemplate RestTemplate;


    @Autowired
    private RenZhengShuXing shu;

    private Map<String,Object> TokenMap=null;
    private Long time=null;
    private String[] qingqiu={"/"};


    /**
     *   "access_token": "73acb581-3202-4ad6-98e5-e5d94817e9cf",
         "token_type": "bearer",
         "refresh_token": "83126f66-99e2-4e7d-8310-c1e629b0a6e2",
         "expires_in": 399999,
         "scope": "all"
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        RequestPath path = request.getPath();
        for (String s:qingqiu){
            if (s.equals(path.toString())){
                return chain.filter(exchange);
            }
        }


        if (time==null){  //用于存储首次生成token的时间，方便刷新token
            time=System.currentTimeMillis()/1000;
        }
        Map zhang=null; //存储请求授权地址的头部信息
        if (TokenMap==null){  //首次获取授权token
            TokenMap=new HashMap<>();
            HttpHeaders maps=new HttpHeaders();
            maps.add("client_id",shu.getClient_id());
            maps.add("client_secret",shu.getClient_secret());
            maps.add("grant_type",shu.getGrant_type());
            maps.add("username",shu.getUsername());
            maps.add("password",shu.getPassword());
            zhang = RestTemplate.postForObject(shu.getPath(),maps,Map.class);
            TokenMap=zhang;
            AfterRenZheng(TokenMap, request);
            return chain.filter(exchange);

        }

        Integer ll = (Integer) TokenMap.get("expires_in");  //获取创建token的有效期
//        Long ll=Long.parseLong(s1);
        Long currentTime=System.currentTimeMillis()/1000; //当前时间
        Long ChaTime=currentTime-time;   //获取token后相差的时间


        if (TokenMap.size()>0&&ChaTime<(ll*0.8)&&ChaTime>0){ //token的有效期小于token的生成的期限的0.8,使用原有token

            if (currentTime<10&&currentTime>0){ //token有效期过短，直接刷新

                refreshToken(zhang);
            }
             AfterRenZheng(TokenMap, request);
             return chain.filter(exchange);

        }else if(TokenMap.size()>0&&ChaTime>(ll*0.8)&&ChaTime<ll){  //token有效期超过期限的0.8，刷新token
            refreshToken(zhang);
            AfterRenZheng(TokenMap, request);
            return chain.filter(exchange);


        }else {  //token过期，创建新的token

                HttpHeaders maps=new HttpHeaders();
                maps.add("client_id",shu.getClient_id());
                maps.add("client_secret",shu.getClient_secret());
                maps.add("grant_type",shu.getGrant_type());
                maps.add("username",shu.getUsername());
                maps.add("password",shu.getPassword());
                zhang = RestTemplate.postForObject(shu.getPath(),maps,Map.class);
                TokenMap=zhang;
                time=System.currentTimeMillis()/1000;
                AfterRenZheng(TokenMap, request);
                return chain.filter(exchange);



        }

    }

    /**
     * 访问资源服务器
     * @param maps
     * @param request
     * @return
     */
    public ServerHttpRequest AfterRenZheng(Map maps,ServerHttpRequest request){

        String access_token = (String) TokenMap.get("access_token");
        String token_type= (String) TokenMap.get("token_type");
        String RenzhengToken=token_type+" "+access_token;
        ServerHttpRequest authorization = request.mutate().header("Authorization", RenzhengToken).build();

        return authorization;
    }

    /**
     * 刷新 Token
     * @param zhang
     */
    public void refreshToken(Map zhang){

        HttpHeaders maps=new HttpHeaders();
        String refresh_token = (String) TokenMap.get("refresh_token");
        maps.add("grant_type","refresh_token");
        maps.add("refresh_token",refresh_token);
        zhang = RestTemplate.postForObject(shu.getPath(),maps,Map.class);
        TokenMap=zhang;
        time=System.currentTimeMillis()/1000;

    }
//    public boolean checkExceptionQingkuang(){
//
//        return false;
//    }
}
