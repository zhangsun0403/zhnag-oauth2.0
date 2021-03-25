package zhang.getway_zhang.hello;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix = "token")
@Data
public class RenZhengShuXing { //基于密码授权


    private String path; //token授权地址
    private String client_id; //客户端 id标识
    private String client_secret; //客户端 秘钥
    private String grant_type; //授权类型
    private String username; //用户名
    private String password;  //密码

}
