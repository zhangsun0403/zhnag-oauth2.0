package zhang.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class authoTest {


    private String path="http://localhost:9005/user";

    @Resource
    private RestTemplate RestTemplate;

    @GetMapping("/oauth/test")  //远程测试
    public String oauthTest(){

        return RestTemplate.postForObject(path,null,String.class)+"...success ";
    }

}
